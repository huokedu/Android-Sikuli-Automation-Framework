/**
 *  Limit preSet method actions that run @BeforeClass to those that cannot fail or make sure that you catch all of the exceptions
 *  or else if a failure occurs it will terminate the entire suite and move on.
 *  Bottom line, you won't have any test results if something fails here and it is not caught and processed.
 **/