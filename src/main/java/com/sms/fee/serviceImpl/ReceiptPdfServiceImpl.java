package com.sms.fee.serviceImpl;


import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import com.sms.exception.BusinessException;
import com.sms.fee.entity.StudentFeeReceipt;
import com.sms.fee.service.ReceiptPdfService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ReceiptPdfServiceImpl
        implements ReceiptPdfService {


    @Override
    public byte[] generateReceiptPdf(
            StudentFeeReceipt receipt
    ){


        try {


            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();



            Document document =
                    new Document();



            PdfWriter.getInstance(
                    document,
                    output
            );


            document.open();



            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            18
                    );



            document.add(
                    new Paragraph(
                            "University Fee Receipt",
                            titleFont
                    )
            );



            document.add(
                    new Paragraph(
                            "================================="
                    )
            );



            document.add(
                    new Paragraph(
                            "Receipt Number : "
                            + receipt.getReceiptNumber()
                    )
            );


            document.add(
                    new Paragraph(
                            "Receipt Date : "
                            + receipt.getReceiptDate()
                    )
            );



            document.add(
                    new Paragraph(
                            "---------------------------------"
                    )
            );



            // Student Details


            document.add(
                    new Paragraph(
                            "Student Name : "
                            + receipt.getStudentName()
                    )
            );


            document.add(
                    new Paragraph(
                            "Roll Number : "
                            + receipt.getRollNumber()
                    )
            );



            document.add(
                    new Paragraph(
                            "Admission Number : "
                            + receipt.getAdmissionNumber()
                    )
            );



            document.add(
                    new Paragraph(
                            "Academic Year : "
                            + receipt.getAcademicYear()
                    )
            );



            document.add(
                    new Paragraph(
                            "Course : "
                            + receipt.getCourseName()
                    )
            );



            document.add(
                    new Paragraph(
                            "Department : "
                            + receipt.getDepartmentName()
                    )
            );



            document.add(
                    new Paragraph(
                            "Semester : "
                            + receipt.getSemesterName()
                    )
            );



            document.add(
                    new Paragraph(
                            "---------------------------------"
                    )
            );



            // Fee Details


            document.add(
                    new Paragraph(
                            "Total Fee : "
                            + receipt.getTotalFee()
                    )
            );


            document.add(
                    new Paragraph(
                            "Scholarship Amount : "
                            + receipt.getScholarshipAmount()
                    )
            );



            document.add(
                    new Paragraph(
                            "Final Payable Amount : "
                            + receipt.getFinalPayableAmount()
                    )
            );



            document.add(
                    new Paragraph(
                            "Paid Amount : "
                            + receipt.getPaidAmount()
                    )
            );



            document.add(
                    new Paragraph(
                            "Due Amount : "
                            + receipt.getDueAmount()
                    )
            );



            document.add(
                    new Paragraph(
                            "---------------------------------"
                    )
            );



            // Payment Details


            document.add(
                    new Paragraph(
                            "Payment Status : "
                            + receipt.getPaymentStatus()
                    )
            );



            document.add(
                    new Paragraph(
                            "Payment Mode : "
                            + receipt.getPaymentMode()
                    )
            );



            document.add(
                    new Paragraph(
                            "Payment Date : "
                            + receipt.getPaymentDate()
                    )
            );



            document.add(
                    new Paragraph(
                            "Transaction Reference : "
                            + receipt.getTransactionReference()
                    )
            );



            if(receipt.getRemarks()!=null){

                document.add(
                        new Paragraph(
                                "Remarks : "
                                + receipt.getRemarks()
                        )
                );
            }



            document.close();



            return output.toByteArray();



        }
        catch(Exception e){


            throw new BusinessException(
                    "Unable to generate receipt PDF"
            );

        }

    }

}