package com.wk.app.couchbase.repository;

import com.wk.app.couchbase.model.Sms;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mikhail.pasenko on 20.03.2017.
 */
@Repository
public interface SmsRepository extends CouchbaseRepository<Sms, Long> {
}
