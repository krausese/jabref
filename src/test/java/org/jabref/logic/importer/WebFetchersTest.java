package org.jabref.logic.importer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jabref.logic.importer.fetcher.AbstractIsbnFetcher;
import org.jabref.logic.importer.fetcher.IsbnViaChimboriFetcher;
import org.jabref.logic.importer.fetcher.IsbnViaEbookDeFetcher;
import org.jabref.logic.importer.fetcher.MrDLibFetcher;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class WebFetchersTest {

    Reflections reflections = new Reflections("org.jabref");
    ImportFormatPreferences importFormatPreferences;

    @Before
    public void setUp() throws Exception {
        importFormatPreferences = mock(ImportFormatPreferences.class);
    }

    @Test
    public void getIdBasedFetchersReturnsAllFetcherDerivingFromIdBasedFetcher() throws Exception {
        List<IdBasedFetcher> idFetchers = WebFetchers.getIdBasedFetchers(importFormatPreferences);

        Set<Class<? extends IdBasedFetcher>> expected = reflections.getSubTypesOf(IdBasedFetcher.class);
        expected.remove(AbstractIsbnFetcher.class);
        expected.remove(IdBasedParserFetcher.class);
        // Remove special ISBN fetcher since we don't want to expose them to the user
        expected.remove(IsbnViaChimboriFetcher.class);
        expected.remove(IsbnViaEbookDeFetcher.class);
        assertEquals(expected, getClasses(idFetchers));
    }

    @Test
    public void getEntryBasedFetchersReturnsAllFetcherDerivingFromEntryBasedFetcher() throws Exception {
        List<EntryBasedFetcher> idFetchers = WebFetchers.getEntryBasedFetchers(importFormatPreferences);

        Set<Class<? extends EntryBasedFetcher>> expected = reflections.getSubTypesOf(EntryBasedFetcher.class);
        expected.remove(EntryBasedParserFetcher.class);
        expected.remove(MrDLibFetcher.class);
        assertEquals(expected, getClasses(idFetchers));
    }

    @Test
    public void getSearchBasedFetchersReturnsAllFetcherDerivingFromSearchBasedFetcher() throws Exception {
        List<SearchBasedFetcher> idFetchers = WebFetchers.getSearchBasedFetchers(importFormatPreferences);

        Set<Class<? extends SearchBasedFetcher>> expected = reflections.getSubTypesOf(SearchBasedFetcher.class);
        expected.remove(SearchBasedParserFetcher.class);
        assertEquals(expected, getClasses(idFetchers));
    }

    @Test
    public void getIdFetchersReturnsAllFetcherDerivingFromIdFetcher() throws Exception {
        List<IdFetcher> idFetchers = WebFetchers.getIdFetchers();

        Set<Class<? extends IdFetcher>> expected = reflections.getSubTypesOf(IdFetcher.class);
        expected.remove(IdParserFetcher.class);
        assertEquals(expected, getClasses(idFetchers));
    }

    private Set<? extends Class<?>> getClasses(List<?> objects) {
        return objects.stream().map(Object::getClass).collect(Collectors.toSet());
    }
}
