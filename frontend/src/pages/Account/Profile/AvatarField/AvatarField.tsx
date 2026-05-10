import styles from "./AvatarField.module.css";
import { type ChangeEvent, useRef } from "react";
import useChangeAvatar from "./useChangeAvatar.ts";
import defaultAvatar from "@/assets/defaultAvatar.png";

interface AvatarFieldProps {
  avatarUrl: string | null;
}

const AvatarField = ({ avatarUrl }: AvatarFieldProps) => {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const { changeAvatar, isPending, error } = useChangeAvatar();

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (!file) return;

    changeAvatar(file);

    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
  };

  const handleAvatarClick = () => {
    fileInputRef.current?.click();
  };

  return (
    <div className={styles.wrapper}>
      <button
        type="button"
        className={`${styles.avatarContainer} ${isPending ? styles.loading : ""}`}
        onClick={handleAvatarClick}
        disabled={isPending}
      >
        <img
          src={avatarUrl || defaultAvatar}
          alt="Аватар"
          className={styles.avatarImage}
        />
        <div className={styles.overlay}>
          <span className={styles.overlayText}>
            {isPending ? "Завантаження" : "Змінити фото"}
          </span>
        </div>
      </button>

      <input
        type="file"
        accept="image/*"
        ref={fileInputRef}
        style={{ display: "none" }}
        onChange={handleFileChange}
      />

      {error && <p className={styles.errorText}>{error}</p>}
    </div>
  );
};

export default AvatarField;
