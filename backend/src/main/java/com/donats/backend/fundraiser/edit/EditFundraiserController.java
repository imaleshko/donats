package com.donats.backend.fundraiser.edit;

import com.donats.backend.fundraiser.edit.dto.EditFundraiserRequest;
import com.donats.backend.fundraiser.edit.dto.EditFundraiserResponse;
import com.donats.backend.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraisers")
public class EditFundraiserController {

    private final EditFundraiserService editFundraiserService;

    public EditFundraiserController(EditFundraiserService editFundraiserService) {
        this.editFundraiserService = editFundraiserService;
    }

    @GetMapping("/{fundraiserId}")
    public ResponseEntity<EditFundraiserResponse> getFundraiserForEdit(
            @PathVariable Long fundraiserId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        EditFundraiserResponse response = editFundraiserService.getFundraiserForEdit(userDetails.getId(), fundraiserId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{fundraiserId}")
    public ResponseEntity<Void> updateFundraiser(
            @PathVariable Long fundraiserId,
            @Valid @RequestBody EditFundraiserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        editFundraiserService.updateFundraiser(userDetails.getId(), fundraiserId, request);
        return ResponseEntity.ok().build();
    }
}
