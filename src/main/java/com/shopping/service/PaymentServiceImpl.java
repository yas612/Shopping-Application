package com.shopping.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.shopping.constants.Constants;
import com.shopping.constants.OrderStatus;
import com.shopping.constants.PaymentStatus;
import com.shopping.entity.Cart;
import com.shopping.entity.Payment;
import com.shopping.exception.PaymentException;
import com.shopping.rowmapper.CartRowMapper;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private JavaMailSender mailSender;
	
	Random rand = new Random();
	
	Constants constants = new Constants();
	
	private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
	
	SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MMMM-yyyy");
	
	SimpleDateFormat receiptDateFormat = new SimpleDateFormat("ddMyyyyhhmmss");
	
	//Method to pay

	@Override
	public void pay(String siteURL) throws PaymentException, SQLException {
		
		log.info("Payment process started.");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
		
		List<Cart> cartObj = jdbcTemplate.query(constants.FetchPriceFromCartQuery+"'"+username+"'", new CartRowMapper());
		
		if(cartObj.isEmpty()) {
			log.error("Cart is empty");
			throw new PaymentException("Cart is empty");
		}
		
		Cart cart = cartObj.get(0);
		
		BigDecimal payableAmount = cart.getBagTotal();
		
		if(payableAmount.equals(new BigDecimal("0.0"))){
			log.error("Can't make payment as the cart value is 0.0");
			throw new PaymentException("Can't make payment as the cart value is 0.0");
		}
		
		try {
			log.info("Payment verification email sending process started");
			sendPayMentVerifyEmail(siteURL,payableAmount);
		} catch (UnsupportedEncodingException e) {
			log.error("Erroroccured while sending payment verificartion email");
			log.error(e.getMessage());
		} catch (MessagingException e) {
			log.error("Erroroccured while sending payment verificartion email");
			log.error(e.getMessage());
		}
		catch(Exception e) {
			log.error("Erroroccured while sending payment verificartion email");
			log.error(e.getMessage());
		}
		log.info("Payment verification email sent");
		

	}
	
	//Method to verify the payment process by the user
	
	public void sendPayMentVerifyEmail(String siteURL, BigDecimal amount)
	        throws MessagingException, UnsupportedEncodingException {
		log.info("Payment verification process started.");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
	    String toAddress = username;
	    String fromAddress = "test6121@gmail.com";
	    String senderName = "Shopping Application";
	    String subject = "Please verify your payment";
	    String content = constants.PaymentVerifymailContent;
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	    content = content.replace("[[email]]", username);   
	    content = content.replace("[[price]]", amount.toString());
	    content = content.replace("[[paymentOK]]", constants.paymentOKURL);
	    content = content.replace("[[paymentNOTOK]]", constants.paymentNOTOKURL);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	     
	}
  
	//Method to send the payment receipt upon successful payment process.
	  private void sendPayMentReceiptEmail(Payment payment)
		        throws MessagingException, UnsupportedEncodingException, FileNotFoundException {
		  log.info("Payment receipt process started.");
		    String toAddress = payment.getEmail();
		    String fromAddress = "test6121@gmail.com";
		    String senderName = "Shopping Application";
		    String subject = "Please verify your payment";
		    String content = constants.paymentReceiptMailContent;
		     
		    MimeMessage message = mailSender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message, true);
		     
		    helper.setFrom(fromAddress, senderName);
		    helper.setTo(toAddress);
		    helper.setSubject(subject);
		    
		    String fileName = "paymentReceipt".concat(receiptDateFormat.format(new Date())).concat(".pdf");
		   
		    File pdfFile = new File(constants.paymentReceiptLoc.concat(fileName));  
        // Creating a PdfDocument object
        PdfDocument pdfDoc
            = new PdfDocument(new PdfWriter(pdfFile));
  
        // Creating a Document object
        Document doc = new Document(pdfDoc);
        log.info("Receipt created with name : ".concat(fileName));
        Text header = new Text("Transaction Details");
        Paragraph headerContent = new Paragraph(header);
        headerContent.setTextAlignment(TextAlignment.CENTER);
        
        Text transactionid = new Text("Transaction ID : ".concat(String.valueOf(payment.getTransactionId())));
        Paragraph transactionidContent = new Paragraph(transactionid);
        transactionidContent.setTextAlignment(TextAlignment.CENTER);
        
        Text amount = new Text("Transaction Amount : ".concat(String.valueOf(payment.getAmount())).concat(" Rs"));
        Paragraph amountContent = new Paragraph(amount);
        amountContent.setTextAlignment(TextAlignment.CENTER);
        
        Text date = new Text("Transaction Date : ".concat(payment.getTransactionDate().toString()));
        Paragraph dateContent = new Paragraph(date);
        dateContent.setTextAlignment(TextAlignment.CENTER);
        
        Text status = new Text("Transaction Status : ".concat(payment.getPaymentStatus()));
        Paragraph statusContent = new Paragraph(status);
        statusContent.setTextAlignment(TextAlignment.CENTER);
        
  
  
        // Adding paragraphs to the document
        doc.add(headerContent);
        doc.add(transactionidContent);
        doc.add(amountContent);
        doc.add(dateContent);
        doc.add(statusContent);
 
		    doc.close();
		    helper.addAttachment(fileName, pdfFile);
		    
			content = content.replace("[[paymentOK]]", constants.paymentOKURL);
		     
		   content.replace("[[name]]", payment.getEmail());
		     
		    helper.setText(content, true);
		   log.info("Sending receipt with file name : ".concat(fileName).concat(" Complete details => ").concat(payment.toString()));
		    mailSender.send(message);
		     log.info("Payment receipt sent.");
		}


	  //Method to confirm to make payment.
	  
	@Override
	public String yesResponse() throws ParseException, PaymentException, UnsupportedEncodingException, MessagingException, SQLException {
		log.info("Payment confirmation process started.");
		Payment payment = new Payment();
		
		payment.setTransactionId(rand.nextInt(100000000));  
		 
		
		String date = simpleformat.format(new Date());
		payment.setTransactionDate(simpleformat.parse(date));
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
		
		List<Cart> cartObj = jdbcTemplate.query(constants.FetchPriceFromCartQuery+"'"+username+"'", new CartRowMapper());
		
		if(cartObj.isEmpty()) {
			log.error("Cart is empty");
			throw new PaymentException("Cart is empty");
		}
		
		Cart cart = cartObj.get(0);
		
		BigDecimal payableAmount = cart.getBagTotal();
		
		if(payableAmount.equals(new BigDecimal("0.0"))){
			log.error("Can't make payment as the cart value is 0.0");
			throw new PaymentException("Can't make payment as the cart value is 0.0");
		}
		
		payment.setAmount(payableAmount);
		payment.setEmail(username);
		payment.setPaymentStatus(PaymentStatus.SUCCESS.toString());
		
		jdbcTemplate.update(constants.SuccessFulPaymentInsertQuery, payment.getTransactionId(), payment.getEmail(), 
				payment.getTransactionDate(), payment.getAmount(), payment.getPaymentStatus());
		jdbcTemplate.update(constants.EmptyCartQuery,username);
		try {
			sendPayMentReceiptEmail(payment);
		} catch (UnsupportedEncodingException | FileNotFoundException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jdbcTemplate.update(constants.InsertOrderQuery,rand.nextInt(100000000),username,cart.getAllProductsInCart(),OrderStatus.CONFIRMED.toString());
		
		return "payment is Successfull - Details-".concat(payment.toString());
	}


	//Method to DENY the payment process.
	@Override
	public String noResponse() throws ParseException, SQLException, PaymentException {
		// TODO Auto-generated method stub
		log.info("Payment confirmation process started.");
		Payment payment = new Payment();
		
		payment.setTransactionId(rand.nextInt(100000000));  
		 
		
		String date = simpleformat.format(new Date());
		payment.setTransactionDate(simpleformat.parse(date));
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     String username;

	     if (principal instanceof UserDetails) {
	       username = ((UserDetails)principal).getUsername();
	     } else {
	        username = principal.toString();
	     }
		
	     log.info("Fetching the cart details.");
		List<Cart> cartObj = jdbcTemplate.query(constants.FetchPriceFromCartQuery+"'"+username+"'", new CartRowMapper());
		
		if(cartObj.isEmpty()) {
			log.error("Cart is empty");
			throw new PaymentException("Cart is empty");
		}
		
		Cart cart = cartObj.get(0);
		
		BigDecimal payableAmount = cart.getBagTotal();
		
		if(payableAmount.equals(new BigDecimal("0.0"))){
			log.error("Can't make payment as the cart value is 0.0");
			throw new PaymentException("Can't make payment as the cart value is 0.0");
		}
		
		log.info("Payable amount is : ".concat(String.valueOf(payableAmount)));
		
		payment.setAmount(payableAmount);
		payment.setEmail(username);
		payment.setPaymentStatus(PaymentStatus.FAILED.toString());
		
		jdbcTemplate.update(constants.FailurePaymentInsertQuery, payment.getTransactionId(), payment.getEmail(), 
				payment.getTransactionDate(), payment.getAmount(), payment.getPaymentStatus());
		
		return "Payment status - FAILED";
	}
	  

}
