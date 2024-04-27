package com.ecobill.ecobill.Services.impl;

import com.ecobill.ecobill.domain.dto.InvoiceDto;
import com.ecobill.ecobill.domain.entities.EPREntity;
import com.ecobill.ecobill.domain.entities.InvoiceEntity;
import com.ecobill.ecobill.mappers.impl.InvoiceMapperImpl;
import com.ecobill.ecobill.repositories.InvoiceRepository;
import com.ecobill.ecobill.services.impl.EPRServiceImpl;
import com.ecobill.ecobill.services.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


class InvoiceServiceImplTest {
    @InjectMocks
    private InvoiceServiceImpl invoiceService;
    @Mock
    private InvoiceRepository repository;
    @Mock
    private InvoiceMapperImpl invoiceMapper;
    @Mock
    private EPRServiceImpl eprService;
    @Mock
    private InvoiceServiceImpl invoiceServiceMock;

    private static InvoiceEntity invoice;
    private static List<InvoiceEntity> invoices;
    private static List<InvoiceDto> invoicesDto;
    private static EPREntity epr;
    private static List<EPREntity> eprs;

    @BeforeAll
    public static void setUp() {
        invoice = new InvoiceEntity();
        invoice.setId(888L);
        epr = new EPREntity();
        epr.setId(888L);
        epr.setName("");
        InvoiceEntity tempInvoice;
        InvoiceDto tempInvoiceDto;
        invoices=new ArrayList<>();
        invoicesDto=new ArrayList<>();
        for(Long i=1L;i<=10L;i++){
            tempInvoice = new InvoiceEntity();
            tempInvoiceDto=new InvoiceDto();
            tempInvoiceDto.setId(i);
            tempInvoice.setId(i);
            invoices.add(tempInvoice);
            invoicesDto.add(tempInvoiceDto);
        }
        EPREntity temp2;
        eprs=new ArrayList<>();
        for(Long i=1L;i<=5L;i++){
            temp2 = new EPREntity();
            temp2.setId(i);
            temp2.setName("");
            eprs.add(temp2);
        }


    }
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEPR() {
        when(eprService.getEPRByName("")).thenReturn(epr);
        when(repository.findAllByEpr(epr)).thenReturn(invoices);
        when(invoiceMapper.mapTo(any(InvoiceEntity.class))).thenReturn(new InvoiceDto());
        List<InvoiceDto> response=invoiceService.getByEPR("");
        assertNotNull(response);
        assertEquals(invoices.size(),response.size());
        Mockito.verify(invoiceMapper, times(invoices.size())).mapTo(any(InvoiceEntity.class));
    }

    @Test
    public void testFindByEPR_when_EPR_is_null() {
        when(repository.findAllByEpr(null)).thenReturn(null);
        List<InvoiceDto> response=invoiceService.getByEPR("");
        assertNull(response);
    }

    @Test
    public void testFindByEPRCategory() {
        when(eprService.getByCategory("")).thenReturn(eprs);
        when(eprService.getEPRByName("")).thenReturn(epr);
        when(repository.findAllByEpr(any(EPREntity.class))).thenReturn(invoices);
        when(invoiceMapper.mapTo(any(InvoiceEntity.class))).thenReturn(new InvoiceDto());
        List<InvoiceDto> response=invoiceService.getByEPRCategory("");
        assertNotNull(response);
        assertEquals(invoices.size()*5,response.size());
    }

    @Test
    public void testFindByEPRCategory_when_eprs_is_empty() {
        when(eprService.getByCategory("")).thenReturn(new ArrayList<>());
        when(eprService.getEPRByName("")).thenReturn(epr);
        when(repository.findAllByEpr(any(EPREntity.class))).thenReturn(invoices);
        when(invoiceMapper.mapTo(any(InvoiceEntity.class))).thenReturn(new InvoiceDto());
        List<InvoiceDto> response=invoiceService.getByEPRCategory("");
        assertEquals(0,response.size());
    }

    @Test
    public void testFindByEPRCategory_when_Invoices_is_empty() {
        when(eprService.getByCategory("")).thenReturn(eprs);
        when(eprService.getEPRByName("")).thenReturn(epr);
        when(repository.findAllByEpr(any(EPREntity.class))).thenReturn(new ArrayList<>());
        when(invoiceMapper.mapTo(any(InvoiceEntity.class))).thenReturn(new InvoiceDto());
        List<InvoiceDto> response=invoiceService.getByEPRCategory("");
        assertEquals(0,response.size());
    }

    @Test
    public void testGetInvoicesForUserInRange() {
        Long userNumber = 353535L;
        int start = 5;
        int end = 10;
        int limit = end - start + 1;
        int offset = start - 1;

        // Create a list of mock InvoiceEntity objects
        List<InvoiceEntity> mockInvoiceEntities = IntStream.range(0, limit)
                .mapToObj(i -> new InvoiceEntity())
                .collect(Collectors.toList());

        // Create a list of mock InvoiceDto objects
        List<InvoiceDto> mockInvoiceDtos = IntStream.range(0, limit)
                .mapToObj(i -> new InvoiceDto())
                .collect(Collectors.toList());

        // Define the behavior of the repository mock
        when(repository.findInvoicesByCustomerPhoneNumberWithOffset(userNumber, limit, offset))
                .thenReturn(mockInvoiceEntities);

        // Define the behavior of the mapper mock
        when(invoiceMapper.mapTo(any(InvoiceEntity.class)))
                .thenReturn(new InvoiceDto()); // Assuming a simple mapping for demonstration

        // Call the method under test
        List<InvoiceDto> result = invoiceService.getInvoicesForUserInRange(userNumber, start, end);

        // Assertions
        assertEquals(mockInvoiceDtos.size(), result.size(), "The size of the result list should match the expected number of invoices.");
        verify(repository, times(1)).findInvoicesByCustomerPhoneNumberWithOffset(userNumber, limit, offset);
        verify(invoiceMapper, times(mockInvoiceEntities.size())).mapTo(any(InvoiceEntity.class));
    }

    @Test
    public void testPrintUserStatistics() {
        Long userNumber = 123L;
        int numberOfMonths = 1;
        Timestamp lower = Timestamp.valueOf(LocalDateTime.now().minusMonths(numberOfMonths));
        Timestamp upper = Timestamp.valueOf(LocalDateTime.now());

        // Mock the InvoiceDto list
        List<InvoiceDto> mockInvoices = Arrays.asList(
                // Add mock InvoiceDto objects here
                // new InvoiceDto(...), new InvoiceDto(...), ...
        );

        // Mock the behavior of the repository and mapper
        when(repository.findAllByCustomerPhoneNumberAndCreationDateBetween(eq(userNumber), eq(lower), eq(upper)))
                .thenReturn(invoices);
        when(invoiceMapper.mapTo(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method to test
        List<String> statistics = invoiceService.printUserStatistics(userNumber, numberOfMonths);

        // Verify the interactions with the mocks
        verify(repository).findAllByCustomerPhoneNumberAndCreationDateBetween(eq(userNumber), eq(lower), eq(upper));
        verify(invoiceMapper, times(mockInvoices.size())).mapTo(any());

        // Assert the results
        assertNotNull(statistics);
        assertEquals(6, statistics.size());
        // Add more assertions to check the correctness of each statistic
        // assertEquals(expectedValue, statistics.get(index));
    }
}