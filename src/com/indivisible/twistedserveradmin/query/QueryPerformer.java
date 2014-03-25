package com.indivisible.twistedserveradmin.query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.joda.time.DateTime;
import com.indivisible.twistedserveradmin.servers.MinecraftServer;
import com.indivisible.twistedserveradmin.system.Main;


/**
 * Class to take care of running threaded Queries on Servers' status
 * 
 * @author indiv
 */
public class QueryPerformer
{

    //TODO: Make a parent Query class for future expansion.

    ///////////////////////////////////////////////////////
    ////    data 
    ///////////////////////////////////////////////////////

    private List<MinecraftServer> serversIn;
    private List<MinecraftServer> serversOut;

    public static final long DEFAULT_QUERY_VALID_AGE = 8000L;   //TODO: read from app settings
    private static final int DEFAULT_MAX_THREADS = 3;           //TODO: Move max threads to app settings
    private static final String TAG = "QueryPerformer";


    ///////////////////////////////////////////////////////
    ////    constructors
    ///////////////////////////////////////////////////////

    /**
     * Create a new QueryPerformer for the supplied List of Servers
     * 
     * @param servers
     */
    public QueryPerformer(List<MinecraftServer> servers)
    {
        this.serversIn = servers;
        this.serversOut = new ArrayList<MinecraftServer>();
    }


    ///////////////////////////////////////////////////////
    ////    public query methods
    ///////////////////////////////////////////////////////

    /**
     * Refresh all Servers' Queries regardless of last check.
     * 
     * @return
     */
    public List<MinecraftServer> queryAllServers()
    {
        long timeStarted = DateTime.now().getMillis();
        ExecutorService executor = Executors
                .newFixedThreadPool(QueryPerformer.DEFAULT_MAX_THREADS);
        List<Future<MinecraftServer>> futureList = new ArrayList<Future<MinecraftServer>>();
        for (MinecraftServer server : serversIn)
        {
            Callable<MinecraftServer> worker = new CallQuery(server);
            Future<MinecraftServer> submit = executor.submit(worker);
            futureList.add(submit);
        }
        for (Future<MinecraftServer> future : futureList)
        {
            try
            {
                serversOut.add(future.get());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        long secsDiff = (DateTime.now().getMillis() - timeStarted) / 1000;
        Main.myLog.info(TAG, String.format("Queried %d of %d Servers in %.2f seconds.",
                                           serversOut.size(),
                                           serversIn.size(),
                                           secsDiff));
        return serversOut;
    }

    /**
     * Refresh or run new Queries for all Servers with stale info.
     * 
     * @param maxAge
     *        the maximum acceptable Query age in milliseconds.
     * @return
     */
    public List<MinecraftServer> queryAllServers(long maxAge)
    {
        long timeStarted = DateTime.now().getMillis();
        ExecutorService executor = Executors
                .newFixedThreadPool(QueryPerformer.DEFAULT_MAX_THREADS);
        List<Future<MinecraftServer>> futureList = new ArrayList<Future<MinecraftServer>>();
        for (MinecraftServer server : serversIn)
        {
            if (queryStillValid(server, maxAge))
            {
                Main.myLog.verbose(TAG, "Query still valid for [" + server.getName()
                        + "]: " + server.getServerStatus().toString());
                continue;
            }
            else
            {
                Main.myLog.verbose(TAG, "No valid query for [" + server.getName() + "]: "
                        + server.getServerStatus().toString());
            }
            Callable<MinecraftServer> worker = new CallQuery(server);
            Future<MinecraftServer> submit = executor.submit(worker);
            futureList.add(submit);
        }
        for (Future<MinecraftServer> future : futureList)
        {
            try
            {
                serversOut.add(future.get());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        long secsDiff = (DateTime.now().getMillis() - timeStarted) / 1000;
        Main.myLog.info(TAG, String.format("Queried %d of %d Servers in %.2f seconds.",
                                           serversOut.size(),
                                           serversIn.size(),
                                           secsDiff));
        return serversOut;
    }

    ///////////////////////////////////////////////////////
    ////    private methods
    ///////////////////////////////////////////////////////

    /**
     * Test whether or not a Server still has a query that is valid for the
     * defined time scale.
     * 
     * @param server
     * @return
     */
    private boolean queryStillValid(MinecraftServer server, long validQueryMaxAge)
    {
        if (!server.hasQuery())
        {
            return false;
        }
        MinecraftServerQuery query = server.getQuery();
        long now = DateTime.now().getMillis();
        long lastRun = query.getTimeLastRun();
        if (lastRun == 0L || now - lastRun > validQueryMaxAge)
        {
            return false;
        }
        else
        {
            Main.myLog.info(TAG, "Server's Query still valid: " + server.getName());
            return true;
        }
    }


    ///////////////////////////////////////////////////////
    ////    threaded query class
    ///////////////////////////////////////////////////////

    /**
     * Class to run a threaded Server Query
     * 
     * @author indiv
     */
    private class CallQuery
            implements Callable<MinecraftServer>
    {

        //TODO: Custom query timeouts pass on to query

        private MinecraftServer server;

        protected CallQuery(MinecraftServer server)
        {
            this.server = server;
        }

        @Override
        public MinecraftServer call() throws Exception
        {
            server.performQuery();
            return server;
        }
    }


}
