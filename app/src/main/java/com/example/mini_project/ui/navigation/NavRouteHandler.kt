package com.example.mini_project.ui.navigation


/**
 * Interface to supply the navigation route and arguments for the app
 */
interface NavRouteHandler {

    /**
     * Name for the route, will be overridden with enum.name
     */
    val routeString: String

    /**
     * String resource id that contains title, which is displayed on the specific screens top bar.
     */
    val topBarTitleResource: Int


}