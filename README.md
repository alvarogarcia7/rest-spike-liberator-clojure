# rest-spike-liberator-clojure

  * A spike of the liberator framework, in clojure.
  * Only a backend, to be consumed via REST API
  * Multi-tenant structure
  * In-memory (``atom``) persistence
  * 
  
## Deployment

Can be used in an embedded server or deployed to an application server

* Standalone server 
  * compile and package: ``lein ring uberwar``
  * then deploy
  * has been tested in a TomEE JaxRS 1.7.3, under Windows 7 64bits
 
 

* Embedded server
  * ``lein ring server``

