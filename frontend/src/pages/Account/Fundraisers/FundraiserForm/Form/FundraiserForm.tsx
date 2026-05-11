import {
  type ChangeEvent,
  type DragEvent,
  type SubmitEvent,
  useRef,
  useState,
} from "react";
import MDEditor from "@uiw/react-md-editor";
import styles from "./FundraiserForm.module.css";
import type {
  INITIAL_FORM,
  NewImageEntry,
} from "@/pages/Account/Fundraisers/FundraiserForm/Form/useFundraiserForm.ts";

interface FundraiserFormProps {
  title: string;
  formData: typeof INITIAL_FORM;
  errors: Record<string, string>;
  isPending: boolean;
  serverError?: string | null;
  originalSlug?: string;
  retainedImages: string[];
  newImages: NewImageEntry[];
  onFieldChange: (e: ChangeEvent<HTMLInputElement>) => void;
  onDescriptionChange: (value?: string) => void;
  onRemoveRetainedImage?: (url: string) => void;
  onRemoveNewImage: (index: number) => void;
  onAddFiles: (files: FileList | null) => void;
  onSubmit: (e: SubmitEvent) => void;
  submitLabel: string;
}

export const FundraiserForm = ({
  title,
  submitLabel,
  formData,
  errors,
  isPending,
  serverError,
  originalSlug,
  retainedImages,
  newImages,
  onFieldChange,
  onDescriptionChange,
  onRemoveRetainedImage,
  onRemoveNewImage,
  onAddFiles,
  onSubmit,
}: FundraiserFormProps) => {
  const [isDragActive, setIsDragActive] = useState(false);
  const fileInputRef = useRef<HTMLInputElement>(null);

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={onSubmit}>
        <h2 className={styles.formTitle}>{title}</h2>

        {serverError && (
          <p className={`${styles.errorText} ${styles.serverError}`}>
            {serverError}
          </p>
        )}

        <div className={styles.inputs}>
          <div className={styles.field}>
            <label htmlFor="title" className={styles.label}>
              Назва збору
            </label>
            <input
              id="title"
              name="title"
              type="text"
              className={styles.formInput}
              value={formData.title}
              onChange={onFieldChange}
            />
            {errors.title && <p className={styles.errorText}>{errors.title}</p>}
          </div>

          <div className={styles.field}>
            <label htmlFor="slug" className={styles.label}>
              Slug для посилання
            </label>
            <input
              id="slug"
              name="slug"
              type="text"
              className={styles.formInput}
              value={formData.slug}
              onChange={onFieldChange}
            />
            {originalSlug && formData.slug !== originalSlug && (
              <div className={styles.errorText}>
                Зміна slug призведе до того, що всі попередні посилання стануть
                недійсними
              </div>
            )}
            {errors.slug && <p className={styles.errorText}>{errors.slug}</p>}
          </div>

          <div className={styles.field}>
            <label htmlFor="description" className={styles.label}>
              Опис збору
            </label>
            <div data-color-mode="dark" className={styles.editorWrapper}>
              <MDEditor
                value={formData.description}
                onChange={onDescriptionChange}
                height={300}
              />
            </div>
            {errors.description && (
              <p className={styles.errorText}>{errors.description}</p>
            )}
          </div>

          <div className={styles.field}>
            <label htmlFor="goal" className={styles.label}>
              Фінансова ціль (опціонально)
            </label>
            <input
              id="goal"
              name="goal"
              type="number"
              min="10"
              className={styles.formInput}
              value={formData.goal}
              onChange={onFieldChange}
            />
            {errors.goal && <p className={styles.errorText}>{errors.goal}</p>}
          </div>

          <div className={styles.field}>
            <label htmlFor="images" className={styles.label}>
              Фотографії для збору
            </label>
            <div
              className={`${styles.dropzone} ${isDragActive ? styles.dropzoneActive : ""}`}
              onClick={() => fileInputRef.current?.click()}
              onDragOver={(e: DragEvent<HTMLDivElement>) => {
                e.preventDefault();
                setIsDragActive(true);
              }}
              onDragLeave={(e: DragEvent<HTMLDivElement>) => {
                e.preventDefault();
                setIsDragActive(false);
              }}
              onDrop={(e: DragEvent<HTMLDivElement>) => {
                e.preventDefault();
                setIsDragActive(false);
                onAddFiles(e.dataTransfer?.files ?? null);
              }}
            >
              <p className={styles.dropzoneText}>
                Перетягніть фотографії або натисніть для вибору
              </p>
              <input
                id="images"
                type="file"
                multiple
                accept="image/*"
                ref={fileInputRef}
                className={styles.hiddenInput}
                onChange={(e) => onAddFiles(e.target.files)}
              />
            </div>
          </div>

          {errors.images && <p className={styles.errorText}>{errors.images}</p>}

          {(retainedImages.length > 0 || newImages.length > 0) && (
            <div className={styles.previewGrid}>
              {retainedImages.map((url) => (
                <div key={url} className={styles.previewItem}>
                  <img
                    src={url}
                    className={styles.previewImage}
                    alt="Збережене фото"
                  />
                  <button
                    type="button"
                    className={styles.removeButton}
                    onClick={() => onRemoveRetainedImage?.(url)}
                  >
                    ✕
                  </button>
                </div>
              ))}
              {newImages.map(({ previewUrl }, i) => (
                <div key={previewUrl} className={styles.previewItem}>
                  <img
                    src={previewUrl}
                    className={styles.previewImage}
                    alt="Нове фото"
                  />
                  <div className={styles.newBadge}>Нове</div>
                  <button
                    type="button"
                    className={styles.removeButton}
                    onClick={() => onRemoveNewImage(i)}
                  >
                    ✕
                  </button>
                </div>
              ))}
            </div>
          )}

          <button
            type="submit"
            className={styles.submitButton}
            disabled={isPending}
          >
            {isPending ? "Збереження..." : submitLabel}
          </button>
        </div>
      </form>
    </div>
  );
};

export default FundraiserForm;
