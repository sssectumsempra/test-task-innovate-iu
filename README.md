# InnovateIU Test Task

# Document Manager

This is an implementation of a `DocumentManager` that allows saving, searching, and retrieving documents by ID. The project is designed with a focus on clean and readable code, as well as simplicity. The implementation uses in-memory storage to hold the documents.

## Features

- **Save**: Adds a document to the storage, generating a unique ID if the document does not already have one.
- **Search**: Finds documents based on a search request, which can match the title, content, author, and creation date of the documents.
- **Find by ID**: Retrieves a document by its unique ID.

## Structure

- `DocumentManager`: The main class responsible for document management operations.
- `Document`: A data class representing a document with an ID, title, content, author, and creation date.
- `SearchRequest`: A data class used to define the search parameters, including title prefixes, content keywords, author IDs, and creation date ranges.
- `Author`: A data class representing the author of a document.

## How to Run

1. Clone the repository to your local machine:

   ```bash
   git clone git@github.com:sssectumsempra/test-task-innovate-iu.git
