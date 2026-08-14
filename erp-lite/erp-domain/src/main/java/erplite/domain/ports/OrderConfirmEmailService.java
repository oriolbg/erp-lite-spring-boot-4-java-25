package erplite.domain.ports;

import erplite.domain.order.OrderId;
import erplite.domain.shared.Email;
import erplite.domain.shared.Money;

/**
 * Port for email service in order created
 */
public interface OrderConfirmEmailService {

	void sendMail(
		Email email,
		OrderId orderId,
		String orderNumer,
		Money money,
		String customerName,
		Integer itemsCount
	);
}
