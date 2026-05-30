package br.com.barberhub.controller;

import br.com.barberhub.dto.ReviewRequestDTO;
import br.com.barberhub.dto.ReviewResponseDTO;
import br.com.barberhub.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
@Validated
@Tag(name = "Review", description = "Controller for management of Reviews")
public class ReviewController {

    private final ReviewService service;

    @Operation(description = "Returns a list of reviews",
            summary = "Find reviews",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> findAllByBarber() {
        return ResponseEntity.ok(service.findAllByBarber());
    }

    @Operation(description = "Returns a list of reviews filtered by barber",
            summary = "Find reviews by barber",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @GetMapping("/barber/{barberId}")
    public ResponseEntity<List<ReviewResponseDTO>> findByBarberId(@PathVariable Long barberId) {
        return ResponseEntity.ok(service.findByBarber(barberId));
    }

    @Operation(description = "Creates a new review for a barber",
            summary = "Create review",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDTO> createAssessment(@RequestBody @Valid ReviewRequestDTO dto, UriComponentsBuilder uriBuilder) {
        var review = service.createReview(dto);
        var uri = uriBuilder.path("/{id}").buildAndExpand(review.id()).toUri();

        return ResponseEntity.created(uri).body(review);
    }


    @Operation(description = "Deletes a review by ID",
            summary = "Delete review",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        service.deleteReview(id);

        return ResponseEntity.noContent().build();
    }


}
