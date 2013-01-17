# pocw3

## Piece of Crap World Wide Web Server

Pocw3 is an elementary approach to creating a limited functionality, multi-threaded web server. As can be discerned from the HttpRequest class (the Runnable which handles responses for each individual requests), the pocw3 implementation does not closely follow the rfc2616 spec for HTTP 1.1 and is intended for educational or experimental purposes.

### Building & Running

    $ javac *.java
    $ java WebServer <port> #Default port is 31173
