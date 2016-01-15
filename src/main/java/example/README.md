This example directory structure has steps and a example test.

In order to run the example test we need to be running a small local HTTP Server that will respond to jmeter get 
requests.  To simplify this we are using python's SimpleHTTPServer which will serve out files in the directory 
that it is run from.  We have provided an html file in the resources of this project.

You will need to have python installed in order to run the example.

The following command run from the ExampleFiles directory will serve the Example.html file

    mineraloil-jmeter/src/main/resources/ExampleFiles$ python -m SimpleHTTPServer

Once the simple http server is running you should be able to run the LoginExample test.

Output of simple http server should show:

    /mineraloil-jmeter/src/main/resources/ExampleFiles$ python -m SimpleHTTPServer
    Serving HTTP on 0.0.0.0 port 8000 ...
    10.14.240.174 - - [15/Jan/2016 12:00:05] "GET /EXAMPLE.html?user=jsmith&password=Demo1234 HTTP/1.1" 200 -

Test results should show that the assertion was successful and found the expected data

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <httpSample t="75" lt="74" ts="1452880805930" s="true" lb="/EXAMPLE.html GET TRUE" rc="200" tn="Login Thread Group 1-1" by="539" sc="1" ec="0" ng="1" na="1" hn="LT3192.local">
        <assertionResult>
            <name>Assert user is logged in</name>
            <failure>false</failure>
            <error>false</error>
        </assertionResult>
    </httpSample>
