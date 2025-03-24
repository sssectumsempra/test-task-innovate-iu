package com.test;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * For implement this task focus on clear code, and make this solution as simple readable as possible
 * Don't worry about performance, concurrency, etc
 * You can use in Memory collection for sore data
 * <p>
 * Please, don't change class name, and signature for methods save, search, findById
 * Implementations should be in a single class
 * This class could be auto tested
 */
public class DocumentManager {

    private final Map<String, Document> storage = new HashMap<>();

    /**
     * Implementation of this method should upsert the document to your storage
     * And generate unique id if it does not exist, don't change [created] field
     *
     * @param document - document content and author data
     * @return saved document
     */
    public Document save(Document document) {
        Document savedDocument = document;
        if (document.getId() == null) {
            savedDocument = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .title(document.getTitle())
                    .content(document.getContent())
                    .author(document.getAuthor())
                    .created(Instant.now())
                    .build();
        }
        storage.put(savedDocument.getId(), savedDocument);
        return savedDocument;
    }

    /**
     * Implementation this method should find documents which match with request
     *
     * @param request - search request, each field could be null
     * @return list matched documents
     */
    public List<Document> search(SearchRequest request) {
        if (request == null) {
            return new ArrayList<>(storage.values());
        }
        return storage.values().stream()
                .filter(document -> filterByPrefixes(document, request.getTitlePrefixes()))
                .filter(document -> filterByContents(document, request.getContainsContents()))
                .filter(document -> filterByAuthorIds(document, request.getAuthorIds()))
                .filter(document -> filterByCreatedFrom(document, request.getCreatedFrom()))
                .filter(document -> filterByCreatedTo(document, request.getCreatedTo()))
                .toList();
    }

    private boolean filterByPrefixes(Document document, List<String> titlePrefixes) {
        if (titlePrefixes == null || titlePrefixes.isEmpty()) {
            return true;
        }
        return titlePrefixes.stream().anyMatch(prefix -> document.getTitle().startsWith(prefix));
    }

    private boolean filterByContents(Document document, List<String> containsContents) {
        if (containsContents == null || containsContents.isEmpty()) {
            return true;
        }
        return containsContents.stream().anyMatch(content -> document.getContent().contains(content));
    }

    private boolean filterByAuthorIds(Document document, List<String> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) {
            return true;
        }
        return authorIds.contains(document.getAuthor().getId());
    }

    private boolean filterByCreatedFrom(Document document, Instant createdFrom) {
        if (createdFrom == null) {
            return true;
        }
        return !document.getCreated().isBefore(createdFrom);
    }

    private boolean filterByCreatedTo(Document document, Instant createdTo) {
        if (createdTo == null) {
            return true;
        }
        return !document.getCreated().isAfter(createdTo);
    }

    /**
     * Implementation this method should find document by id
     *
     * @param id - document id
     * @return optional document
     */
    public Optional<Document> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Data
    @Builder
    public static class SearchRequest {
        private List<String> titlePrefixes;
        private List<String> containsContents;
        private List<String> authorIds;
        private Instant createdFrom;
        private Instant createdTo;
    }

    @Data
    @Builder
    public static class Document {
        private String id;
        private String title;
        private String content;
        private Author author;
        private Instant created;
    }

    @Data
    @Builder
    public static class Author {
        private String id;
        private String name;
    }
}
