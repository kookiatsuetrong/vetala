public class Email {

	public void sendActivationCode(String target, String code) {
		EmailSender es = new EmailSender();
		es.target  = target;
		es.subject = "Activation Code";
		es.content = "The activation code is <b>" + 
						code + "</b>";
		es.start();
	}
	
	public void sendResetCode(String target, String code) {
		EmailSender es = new EmailSender();
		es.target  = target;
		es.subject = "Reset Code";
		es.content = "The reset code is <b>" + 
						code + "</b>";
		es.start();
	}
	
	public void sendWelcome(String target) {
		EmailSender es = new EmailSender();
		es.target  = target;
		es.subject = "Your email has been registered";
		es.content = "Thank you for registering your email to the system.";
		es.start();
	}
	
	public void sendResetConfirmation(String target) {
		EmailSender es = new EmailSender();
		es.target  = target;
		es.subject = "Your password has been changed";
		es.content = "Your password has been changed successfully.";
		es.start();
	}
	
	public void sendContactSavedConfirmation(String target) {
		EmailSender es = new EmailSender();
		es.target  = target;
		es.subject = "Thank you for contacting us";
		es.content = "We have received your message.";
		es.start();
	}
}
