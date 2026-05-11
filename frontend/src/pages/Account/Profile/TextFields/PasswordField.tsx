import styles from "./Field.module.css";
import editIcon from "@/assets/edit.png";
import { type ChangeEvent, useState } from "react";

interface PasswordFieldProps {
  onSave: (oldPass: string, newPass: string, onSuccess: () => void) => void;
  isPending: boolean;
  serverError: string | null;
}

const PasswordField = ({
  onSave,
  isPending,
  serverError,
}: PasswordFieldProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState<Record<string, string>>({});

  const resetForm = () => {
    setOldPassword("");
    setNewPassword("");
    setConfirmPassword("");
    setErrors({});
  };

  const handleEditClick = () => {
    setIsEditing(true);
    resetForm();
  };

  const handleCancel = () => {
    setIsEditing(false);
    resetForm();
  };

  const validate = () => {
    const validationErrors: Record<string, string> = {};

    if (oldPassword.length === 0) {
      validationErrors.oldPassword = "Введіть старий пароль";
    }
    if (newPassword.length < 8) {
      validationErrors.newPassword = "Пароль має містити щонайменше 8 символів";
    }
    if (newPassword !== confirmPassword) {
      validationErrors.confirmPassword = "Паролі не співпадають";
    }

    return validationErrors;
  };

  const handleSave = () => {
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    onSave(oldPassword, newPassword, () => {
      setIsEditing(false);
      resetForm();
    });
  };

  const clearFieldError = (fieldName: string) => {
    if (errors[fieldName]) {
      setErrors((prev) => {
        const newErrors = { ...prev };
        delete newErrors[fieldName];
        return newErrors;
      });
    }
  };

  if (!isEditing) {
    return (
      <div className={styles.wrapper}>
        <label className={styles.label}>Пароль</label>
        <div className={styles.inputGroup}>
          <input
            type="password"
            value="••••••••••••••••"
            readOnly
            className={styles.input}
          />
          <button
            type="button"
            className={styles.button}
            onClick={handleEditClick}
          >
            <img src={editIcon} alt="edit" width={30} height={30} />
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.wrapper}>
      <label className={styles.label}>Пароль</label>

      {serverError && (
        <p className={`${styles.errorText} ${styles.serverError}`}>
          {serverError}
        </p>
      )}

      <div className={styles.editGroup}>
        <input
          type="password"
          value={oldPassword}
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            setOldPassword(e.target.value);
            clearFieldError("oldPassword");
          }}
          placeholder="Старий пароль"
          className={styles.editInput}
        />
        {errors.oldPassword && (
          <p className={styles.errorText}>{errors.oldPassword}</p>
        )}

        <input
          type="password"
          value={newPassword}
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            setNewPassword(e.target.value);
            clearFieldError("newPassword");
          }}
          placeholder="Новий пароль"
          className={styles.editInput}
        />
        {errors.newPassword && (
          <p className={styles.errorText}>{errors.newPassword}</p>
        )}

        <input
          type="password"
          value={confirmPassword}
          onChange={(e: ChangeEvent<HTMLInputElement>) => {
            setConfirmPassword(e.target.value);
            clearFieldError("confirmPassword");
          }}
          placeholder="Повторіть новий пароль"
          className={styles.editInput}
        />
        {errors.confirmPassword && (
          <p className={styles.errorText}>{errors.confirmPassword}</p>
        )}
      </div>

      <div className={styles.actions}>
        <button
          type="button"
          className={styles.saveButton}
          onClick={handleSave}
          disabled={isPending}
        >
          Зберегти
        </button>

        <button
          type="button"
          className={styles.cancelButton}
          onClick={handleCancel}
          disabled={isPending}
        >
          Скасувати
        </button>
      </div>
    </div>
  );
};

export default PasswordField;
