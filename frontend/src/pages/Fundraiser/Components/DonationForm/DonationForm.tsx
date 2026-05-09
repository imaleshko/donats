import styles from "./DonationForm.module.css";
import { type ChangeEvent, type SubmitEvent, useState } from "react";
import { useInitDonation } from "../../Components/DonationForm/useInitDonation.ts";
import { useQueryClient } from "@tanstack/react-query";
import { useParams } from "react-router";

interface DonationFormProps {
  fundraiserId: number;
}

const DonationForm = ({ fundraiserId }: DonationFormProps) => {
  const [formValues, setFormValues] = useState({
    amount: "",
    name: "",
    message: "",
  });

  const [validationErrors, setValidationErrors] = useState<
    Record<string, string>
  >({});
  const [paymentError, setPaymentError] = useState<string | null>(null);

  const queryClient = useQueryClient();
  const { username, slug } = useParams<{ username: string; slug: string }>();
  const {
    initDonation,
    isPending,
    error: serverError,
  } = useInitDonation({
    onSuccessPayment: () => {
      setFormValues({ amount: "", name: "", message: "" });

      void queryClient.invalidateQueries({
        queryKey: ["fundraiser", username, slug],
      });
      void queryClient.invalidateQueries({
        queryKey: ["fundraiser-donations", fundraiserId],
      });
    },
    onFailedPayment: () => {
      setPaymentError("Оплата не пройшла");
    },
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (validationErrors[name]) {
      setValidationErrors((prevErrors) => {
        const newErrors = { ...prevErrors };
        delete newErrors[name];
        return newErrors;
      });
    }

    setFormValues((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const validate = () => {
    const validationErrors: Record<string, string> = {};

    if (!formValues.amount || Number(formValues.amount) < 10) {
      validationErrors.amount = "Мінімальна сума донату - 10 грн";
    }
    if (formValues.name.trim().length === 0) {
      validationErrors.name = "Ім'я не може бути порожнім";
    }

    return validationErrors;
  };

  const handleSubmit = (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    const errors = validate();
    if (Object.keys(errors).length > 0) {
      setValidationErrors(errors);
      return;
    }

    initDonation({
      fundraisingId: fundraiserId,
      amount: Number(formValues.amount),
      name: formValues.name.trim(),
      message: formValues.message.trim(),
    });
  };

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.row}>
          <div className={styles.inputGroup}>
            <input
              type="number"
              placeholder="Сума"
              className={styles.input}
              name="amount"
              value={formValues.amount}
              onChange={handleChange}
              min="10"
            />
            {validationErrors.amount && (
              <span className={styles.errorText}>
                {validationErrors.amount}
              </span>
            )}
          </div>

          <div className={styles.inputGroup}>
            <input
              type="text"
              placeholder="Відображуване ім'я"
              className={styles.input}
              name="name"
              value={formValues.name}
              onChange={handleChange}
            />
            {validationErrors.name && (
              <span className={styles.errorText}>{validationErrors.name}</span>
            )}
          </div>

          <div className={styles.inputGroup}>
            <input
              type="text"
              placeholder="Повідомлення"
              className={styles.input}
              name="message"
              value={formValues.message}
              onChange={handleChange}
            />
          </div>
        </div>

        {serverError && <div className={styles.serverError}>{serverError}</div>}
        {paymentError && (
          <div className={styles.serverError}>{paymentError}</div>
        )}

        <button
          type="submit"
          className={styles.submitButton}
          disabled={isPending}
        >
          {isPending ? "Обробка" : "Здійснити донат"}
        </button>
      </form>
    </div>
  );
};

export default DonationForm;
