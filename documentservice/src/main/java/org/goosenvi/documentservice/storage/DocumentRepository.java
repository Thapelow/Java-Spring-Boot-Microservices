package org.goosenvi.documentservice.storage;

import org.goosenvi.documentservice.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    Document findDocumentBydocId(String docId);
}
