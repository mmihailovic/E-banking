package rs.edu.raf.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.edu.raf.dto.FavouriteRecipientDTO;
import rs.edu.raf.dto.FavouriteRecipientRequestDTO;
import rs.edu.raf.service.FavouriteRecipientService;

import java.util.List;

@RestController
@RequestMapping("/favourite-recipients")
@Tag(name = "FavouriteRecipient", description = "Manage favourite recipient for clients")
@AllArgsConstructor
@SecurityRequirement(name = "jwt")
@CrossOrigin(origins = "*")
public class FavouriteRecipientController {

    private FavouriteRecipientService favouriteRecipientService;

    @ApiOperation(value = "Add favourite recipient")
    @PostMapping
    public ResponseEntity<FavouriteRecipientDTO> addFavouriteRecipient(@RequestBody FavouriteRecipientRequestDTO favouriteRecipientRequestDTO){
        return new ResponseEntity<>(favouriteRecipientService.addFavouriteClient(favouriteRecipientRequestDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all favourite recipients for client")
    @GetMapping
    public ResponseEntity<List<FavouriteRecipientDTO>> getAllFavouriteRecipientsForClient(){
        return new ResponseEntity<>(favouriteRecipientService.findFavouriteClientsForClient(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete favourite recipient entry with id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavouriteRecipientEntryWithId(@PathVariable("id") Long id){
        favouriteRecipientService.deleteFavouriteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "Edit favourite recipient")
    public ResponseEntity<FavouriteRecipientDTO> editFavouriteRecipient(@RequestBody FavouriteRecipientRequestDTO favouriteRecipientRequestDTO) {
        return new ResponseEntity<>(favouriteRecipientService.editFavouriteClient(favouriteRecipientRequestDTO), HttpStatus.OK);
    }

}
