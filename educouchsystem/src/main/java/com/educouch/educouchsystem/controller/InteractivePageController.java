package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.ChapterToReorderDTO;
import com.educouch.educouchsystem.dto.FileItemDTO;
import com.educouch.educouchsystem.dto.PageToReorderDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.service.InteractiveChapterService;
import com.educouch.educouchsystem.service.InteractivePageService;
import com.educouch.educouchsystem.util.comparator.ChapterComparator;
import com.educouch.educouchsystem.util.comparator.PageComparator;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractiveChapterNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractivePageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/interactivePage")
@CrossOrigin
public class InteractivePageController {

    @Autowired
    private InteractivePageService interactivePageService;

    @Autowired
    private InteractiveChapterService interactiveChapterService;

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/{interactiveChapterId}/interactivePages")
    public ResponseEntity<InteractivePage> addInteractivePage(@PathVariable(value="interactiveChapterId") Long interactiveChapterId, @RequestBody InteractivePage interactivePageRequest) {
        try {
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            interactivePageRequest.setInteractiveChapter(interactiveChapter);
            if(interactiveChapter.getInteractivePages() != null) {
                interactiveChapter.getInteractivePages().add(interactivePageRequest);
            } else {
                List<InteractivePage> interactivePageList = new ArrayList<>();
                interactivePageList.add(interactivePageRequest);
                interactiveChapter.setInteractivePages(interactivePageList);
            }
            InteractivePage interactivePage = interactivePageService.saveInteractivePage(interactivePageRequest);
            return new ResponseEntity<>(interactivePage, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/{interactiveChapterId}/interactivePages")
//    public ResponseEntity<InteractivePage> addInteractivePage(@PathVariable(value="interactiveChapterId") Long interactiveChapterId, @RequestBody InteractivePage interactivePageRequest) {
//        try {
//            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
//            interactivePageRequest.setInteractiveChapter(interactiveChapter);
//            if(interactiveChapter.getInteractivePages() != null) {
//                interactiveChapter.getInteractivePages().add(interactivePageRequest);
//            } else {
//                List<InteractivePage> interactivePageList = new ArrayList<>();
//                interactivePageList.add(interactivePageRequest);
//                interactiveChapter.setInteractivePages(interactivePageList);
//            }
//            InteractivePage interactivePage = interactivePageService.saveInteractivePage(interactivePageRequest);
//            return new ResponseEntity<>(interactivePage, HttpStatus.OK);
//        } catch (InteractiveChapterNotFoundException ex) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/interactivePages")
    public ResponseEntity<List<InteractivePage>> getAllInteractiveChapters() {
        List<InteractivePage> allInteractivePages = new ArrayList<>();
        if (!interactivePageService.getAllInteractivePages().isEmpty()) {
            allInteractivePages = interactivePageService.getAllInteractivePages();
            return new ResponseEntity<>(allInteractivePages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/interactivePages/{interactivePageId}")
    public ResponseEntity<InteractivePage> deleteInteractivePage(@PathVariable("interactivePageId") Long interactivePageId) {
        try {
            InteractivePage existingInteractivePage = interactivePageService.getInteractivePageById(interactivePageId);
            InteractiveChapter interactiveChapter = existingInteractivePage.getInteractiveChapter();

            //if the page being deleted is not the last page of the chapter, the pages coming after their page numbers have to be decreased by 1
            List<InteractivePage> interactivePageList = interactiveChapter.getInteractivePages();
            if (interactivePageList.size() != existingInteractivePage.getPageNumber()) {
                for (int i = existingInteractivePage.getPageNumber(); i < interactivePageList.size(); i++ ) {
                    InteractivePage pageToUpdate = interactivePageList.get(i);
                    Integer updatedPageNumber = pageToUpdate.getPageNumber() - 1;
                    pageToUpdate.setPageNumber(updatedPageNumber);
                    interactivePageService.saveInteractivePage(pageToUpdate);
                }
            }

            interactiveChapter.getInteractivePages().remove(existingInteractivePage);
            existingInteractivePage.setInteractiveChapter(null);
            existingInteractivePage.setPageQuiz(null);
            existingInteractivePage.setAttachment(null);

            interactivePageService.deleteInteractivePage(interactivePageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/interactivePages/{interactivePageId}")
    public ResponseEntity<InteractivePage> updateInteractivePage (@RequestBody InteractivePage interactivePage, @PathVariable("interactivePageId") Long interactivePageId) {
        try {
            InteractivePage existingInteractivePage = interactivePageService.getInteractivePageById(interactivePageId);
            existingInteractivePage.setPageDescription(interactivePage.getPageDescription());
            existingInteractivePage.setPageNumber(interactivePage.getPageNumber());
            existingInteractivePage.setPageTitle(interactivePage.getPageTitle());
           // existingInteractivePage.setPageQuestions(interactivePage.getPageQuestions());    const [newTextItemWords, setNewTextItemWords] = useState("");
           // existingInteractivePage.setAttachments(interactivePage.getAttachments());

            interactivePageService.saveInteractivePage(existingInteractivePage);
            return new ResponseEntity<>(existingInteractivePage, HttpStatus.OK);
        }  catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<InteractivePage>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{interactivePageId}/addFileItem")
    public ResponseEntity<InteractivePage> addFileItem(@PathVariable(value="interactivePageId") Long interactivePageId, @RequestBody FileItemDTO fileItemDTORequest) {
        try {
            InteractivePage interactivePage = interactivePageService.getInteractivePageById(interactivePageId);
            if (interactivePage.getAttachment() != null) {
                Long attachmentIdToDelete = interactivePage.getAttachment().getAttachmentId();
                interactivePage.setAttachment(null);
                attachmentService.deleteAttachment(attachmentIdToDelete);
            }
            interactivePage.setAttachment(attachmentService.getAttachment(fileItemDTORequest.getAttachmentId()));
            interactivePageService.saveInteractivePage(interactivePage);
            return new ResponseEntity<>(interactivePage, HttpStatus.OK);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{interactivePageId}/removeFileItem")
    public ResponseEntity<InteractivePage> removeFileItem(@PathVariable(value="interactivePageId") Long interactivePageId) {
        try {
            InteractivePage interactivePage = interactivePageService.getInteractivePageById(interactivePageId);
            if (interactivePage.getAttachment() != null) {
                Long attachmentIdToDelete = interactivePage.getAttachment().getAttachmentId();
                interactivePage.setAttachment(null);
                attachmentService.deleteAttachment(attachmentIdToDelete);
            }
            interactivePageService.saveInteractivePage(interactivePage);
            return new ResponseEntity<>(interactivePage, HttpStatus.OK);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/interactiveChapter/{interactiveChapterId}/interactivePages")
    public ResponseEntity<List<InteractivePage>> getAllInteractivePagesByInteractiveChapterId(@PathVariable("interactiveChapterId") Long interactiveChapterId) {
        try {
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            List<InteractivePage> interactivePageList = new ArrayList<>();
            interactivePageList.addAll(interactiveChapter.getInteractivePages());
            Collections.sort(interactivePageList, new PageComparator());
            return new ResponseEntity<>(interactivePageList, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPageByChapterIdAndPageNumber/{chapterIdPageNumString}")
    public ResponseEntity<InteractivePage> getInteractivePageByInteractiveChapterIdAndPageNumber(@PathVariable("chapterIdPageNumString") String chapterIdPageNumString) {
        try {
            String[] arr = chapterIdPageNumString.split("&");
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(Long.parseLong(arr[0]));
            InteractivePage queriedInteractivePage = new InteractivePage();
            for (InteractivePage interactivePage : interactiveChapter.getInteractivePages()) {
                if (interactivePage.getPageNumber() == Integer.parseInt(arr[1])) {
                    queriedInteractivePage = interactivePage;
                }
            }
            return new ResponseEntity<>(queriedInteractivePage, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{interactiveChapterId}/interactivePages/reorderInteractivePages")
    public ResponseEntity<List<InteractivePage>> reorderInteractivePages(@PathVariable(value="interactiveChapterId") Long interactiveChapterId, @RequestBody List<PageToReorderDTO> pageToReorderDTOList) {
        try {
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            for (PageToReorderDTO pageToReorderDTO : pageToReorderDTOList) {
                InteractivePage interactivePage = interactivePageService.getInteractivePageById(pageToReorderDTO.getPageId());
                interactivePage.setPageNumber(Integer.valueOf(pageToReorderDTO.getPageNumber()));
                interactivePage = interactivePageService.saveInteractivePage(interactivePage);
            }
            List<InteractivePage> interactivePageList = new ArrayList<>();
            interactivePageList.addAll(interactiveChapter.getInteractivePages());
            return new ResponseEntity<>(interactivePageList, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InteractivePageNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
