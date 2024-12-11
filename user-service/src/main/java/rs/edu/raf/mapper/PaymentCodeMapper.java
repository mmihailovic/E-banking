package rs.edu.raf.mapper;

import rs.edu.raf.dto.PaymentCodeDTO;
import rs.edu.raf.model.PaymentCode;

public class PaymentCodeMapper {

    public static PaymentCodeDTO toDTO(PaymentCode source) {
        return new PaymentCodeDTO(source.getId(),
                source.getFormAndBasis(),
                source.getPaymentDescription()
        );
    }

    public static PaymentCode toEntity(PaymentCodeDTO source) {
        PaymentCode paymentCode = new PaymentCode();
        paymentCode.setId(source.id());
        paymentCode.setFormAndBasis(source.formAndBasis());
        paymentCode.setPaymentDescription(source.paymentDescription());

        return paymentCode;
    }

}
