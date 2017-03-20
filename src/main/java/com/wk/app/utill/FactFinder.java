package com.wk.app.utill;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author andrey.trotsenko
 */
public class FactFinder{
    @SuppressWarnings("unchecked")
    public static <T> T findFact(final KieSession session, Class<?> classToFind) {

        ObjectFilter filter = object -> object.getClass().equals(classToFind);

        Collection<FactHandle> factHandles = session.getFactHandles(filter);
        List<T> facts = new ArrayList<T>();
        for (FactHandle handle : factHandles) {
            facts.add((T) session.getObject(handle));
        }

        if (facts.size() == 0) {
            return null;
        }
        // Assumes that the rules will always be generating a single object.
        return facts.get(0);

    }
}
