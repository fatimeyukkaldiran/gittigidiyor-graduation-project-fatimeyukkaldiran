package patika.dev.schoolmanagementsystem.api.controller;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patika.dev.schoolmanagementsystem.entity.ErrorExceptionLogger;
import patika.dev.schoolmanagementsystem.service.ErrorExceptionLoggerService;

import java.util.Date;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class ErrorExceptionLoggerController {

    ErrorExceptionLoggerService errorExceptionLoggerService;

    @Autowired
    public ErrorExceptionLoggerController(ErrorExceptionLoggerService errorExceptionLoggerService) {
        this.errorExceptionLoggerService = errorExceptionLoggerService;
    }

    /**
     *
     * @return All ErrorExceptionLogger
     */
    @GetMapping(value = "")
    public ResponseEntity<List<ErrorExceptionLogger>> getAllErrorExceptionLogger(){
        return new ResponseEntity<>(errorExceptionLoggerService.findAll().get(), HttpStatus.OK);
    }

    /**
     *
     * @param status is error type
     * @return logs according to error type
     */
    @GetMapping(value = "/{status}")
    public ResponseEntity<List<ErrorExceptionLogger>> getAllByStatus(@PathVariable int status){
        return new ResponseEntity<>(errorExceptionLoggerService.findAllByStatus(status).get(),HttpStatus.OK);
    }

    /**
     *
     * @param date first date
     * @return logs between dates
     */
    @PostMapping(value = "/{date}")
    public ResponseEntity<List<ErrorExceptionLogger>> getErrorExceptionLoggerByDateAt(@RequestParam("start")
                                                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date){
        return new ResponseEntity<>(errorExceptionLoggerService.getByTimestampBetween(date).get(),HttpStatus.OK);

    }
}
