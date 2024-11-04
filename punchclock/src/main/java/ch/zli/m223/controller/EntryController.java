package ch.zli.m223.controller;

import ch.zli.m223.dto.EntryDTO;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {
    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<EntryDTO> index() {
        return entryService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public EntryDTO create(EntryDTO entryDTO) {
        Entry entry = toEntity(entryDTO);
        return toDTO(entryService.createEntry(entry));
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry.", description = "Updates an existing entry and returns the updated entry.")
    public EntryDTO update(@PathParam("id") Long id, EntryDTO entryDTO) {
        Entry entry = toEntity(entryDTO);
        return toDTO(entryService.updateEntry(id, entry));
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes an existing entry.")
    public void delete(@PathParam("id") Long id) {
        entryService.deleteEntry(id);
    }

    private EntryDTO toDTO(Entry entry) {
        EntryDTO dto = new EntryDTO();
        dto.setId(entry.getId());
        dto.setCheckIn(entry.getCheckIn());
        dto.setCheckOut(entry.getCheckOut());
        return dto;
    }

    private Entry toEntity(EntryDTO dto) {
        Entry entry = new Entry();
        entry.setId(dto.getId());
        entry.setCheckIn(dto.getCheckIn());
        entry.setCheckOut(dto.getCheckOut());
        return entry;
    }
}