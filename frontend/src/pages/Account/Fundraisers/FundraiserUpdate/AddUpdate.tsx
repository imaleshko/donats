import styles from "./AddUpdate.module.css";
import { useNavigate, useParams } from "react-router";
import { useCreateUpdate } from "./useCreateUpdate.ts";
import { type ChangeEvent, type SubmitEvent, useState } from "react";
import MDEditor from "@uiw/react-md-editor";

const AddUpdate = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { createUpdate, isPending, error: serverError } = useCreateUpdate();

  const [formData, setFormData] = useState({ title: "", message: "" });
  const [errors, setErrors] = useState<Record<string, string>>({});

  const validate = () => {
    const newErrors: Record<string, string> = {};
    if (!formData.title.trim()) newErrors.title = "Назва обов'язкова";
    if (!formData.message.trim())
      newErrors.message = "Текст апдейту обов'язковий";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleFieldChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setErrors((prev) => ({ ...prev, [name]: "" }));
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleMessageChange = (value?: string) => {
    setErrors((prev) => ({ ...prev, message: "" }));
    setFormData((prev) => ({ ...prev, message: value || "" }));
  };

  const handleSubmit = (e: SubmitEvent) => {
    e.preventDefault();
    if (!validate()) return;

    createUpdate(
      { fundraiserId: Number(id), data: formData },
      {
        onSuccess: () => navigate("/account/fundraisers"),
      },
    );
  };

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <h2 className={styles.formTitle}>Додати апдейт до збору</h2>

        {serverError && (
          <p className={`${styles.errorText} ${styles.serverError}`}>
            {serverError}
          </p>
        )}

        <div className={styles.inputs}>
          <label htmlFor="title" className={styles.label}>
            Заголовок апдейту
          </label>
          <input
            id="title"
            name="title"
            type="text"
            placeholder="Введіть текст"
            className={styles.formInput}
            value={formData.title}
            onChange={handleFieldChange}
          />
          {errors.title && <p className={styles.errorText}>{errors.title}</p>}

          <label htmlFor="message" className={styles.label}>
            Текст апдейту
          </label>
          <div data-color-mode="dark" className={styles.editorWrapper}>
            <MDEditor
              value={formData.message}
              onChange={handleMessageChange}
              height={300}
            />
          </div>
          {errors.message && (
            <p className={styles.errorText}>{errors.message}</p>
          )}

          <button
            type="submit"
            className={styles.submitButton}
            disabled={isPending}
          >
            {isPending ? "Публікація" : "Опублікувати апдейт"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddUpdate;
