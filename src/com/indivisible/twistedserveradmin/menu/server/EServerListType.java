package com.indivisible.twistedserveradmin.menu.server;


public enum EServerListType
{
    all,            // all valid servers
    monitored,      // only servers marked as 'monitored'
    status,         // all servers sorted by status
    online,         // only online servers
    offline,        // only offline servers
    error;          // only servers that produced an error on query
}
