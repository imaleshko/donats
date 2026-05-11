import styles from "./Update.module.css";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";
import { useDeleteUpdate } from "@/pages/Fundraiser/Components/Update/useDeleteUpdate.ts";
import DeleteUpdateModal from "@/pages/Fundraiser/Components/Update/DeleteUpdateModal/DeleteUpdateModal.tsx";

interface UpdateProps {
  id: number;
  fundraiserId: number;
  title: string;
  message: string;
  createdAt: string;
  canDelete: boolean;
}

const Update = ({
  id,
  fundraiserId,
  title,
  message,
  createdAt,
  canDelete,
}: UpdateProps) => {
  const date = new Date(createdAt).toLocaleDateString("uk-UA");

  const [isModalOpen, setIsModalOpen] = useState(false);

  const { deleteUpdate, isPending, error } = useDeleteUpdate();

  const handleConfirmDelete = () => {
    deleteUpdate(
      { fundraiserId, updateId: id },
      {
        onSuccess: () => setIsModalOpen(false),
      },
    );
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <h2 className={styles.title}>{title}</h2>
        <span className={styles.date}>{date}</span>
        {canDelete && (
          <button
            className={styles.deleteButton}
            onClick={() => setIsModalOpen(true)}
          >
            Видалити
          </button>
        )}
      </div>

      <MDEditor.Markdown
        source={message}
        style={{
          backgroundColor: "transparent",
          color: "var(--text-grey-color)",
        }}
      />

      {error && <p className={styles.error}>{error}</p>}

      <DeleteUpdateModal
        isOpen={isModalOpen}
        isPending={isPending}
        onClose={() => setIsModalOpen(false)}
        onConfirm={handleConfirmDelete}
      />
    </div>
  );
};

export default Update;
