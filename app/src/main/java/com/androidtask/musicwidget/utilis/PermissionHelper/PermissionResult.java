package com.androidtask.musicwidget.utilis.PermissionHelper;


/**
 * The interface Permission result.
 */
public interface PermissionResult {

     /**
      * Permission granted.
      */
     void permissionGranted();

     /**
      * Permission denied.
      */
     void permissionDenied();

     /**
      * Permission forever denied.
      */
     void permissionForeverDenied();
}
