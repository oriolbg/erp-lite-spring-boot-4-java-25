package erplite.domain.ports.services;


import erplite.domain.entities.order.OrderId;
import erplite.domain.shared.Email;
import erplite.domain.shared.Money;

/**
 * Port for email service in order created
 */
public interface OrderConfirmEmailServicePort {

	void sendMail(
		Email email,
		OrderId orderId,
		String orderNumer,
		Money money,
		String customerName,
		Integer itemsCount
	);
}
