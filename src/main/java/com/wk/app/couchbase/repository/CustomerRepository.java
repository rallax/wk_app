package com.wk.app.couchbase.repository;

import com.wk.app.couchbase.model.Customer;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mikhail.pasenko
 */
@Repository
public interface CustomerRepository extends CouchbaseRepository<Customer, String> {
}
