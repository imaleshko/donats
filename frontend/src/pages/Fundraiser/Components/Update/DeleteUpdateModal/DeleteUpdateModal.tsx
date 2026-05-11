import styles from "@/pages/Account/Fundraisers/CloseFundraiserModal/CloseFundraiserModal.module.css";

interface CloseFundraiserModalProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  isPending?: boolean;
}

const DeleteUpdateModal = ({
  isOpen,
  onClose,
  onConfirm,
  isPending = false,
}: CloseFundraiserModalProps) => {
  if (!isOpen) return null;

  const handleOverlayClick = () => {
    if (!isPending) onClose();
  };

  return (
    <div className={styles.overlay} onClick={handleOverlayClick}>
      <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
        <h3 className={styles.title}>Видалення оновлення</h3>
        <p className={styles.description}>
          Ви впевнені, що хочете видалити це оновлення?
          <br />
          <b>Цю дію неможливо скасувати.</b>
        </p>

        <div className={styles.actions}>
          <button
            className={styles.cancelButton}
            onClick={onClose}
            disabled={isPending}
          >
            Скасувати
          </button>
          <button
            className={styles.confirmButton}
            onClick={onConfirm}
            disabled={isPending}
          >
            {isPending ? "Видалення" : "Підтвердити"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default DeleteUpdateModal;
