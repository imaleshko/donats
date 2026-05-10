import styles from "./FundraiserCard.module.css";
import { Link } from "react-router";
import type { UserFundraiserResponse } from "../../accountApi.ts";

interface FundraiserCardProps {
  fundraiser: UserFundraiserResponse;
  onAddUpdate: (id: number) => void;
  onEdit: (slug: string) => void;
  onClose: (id: number) => void;
}

const FundraiserCard = ({
  fundraiser,
  onAddUpdate,
  onEdit,
  onClose,
}: FundraiserCardProps) => {
  const formattedDate = new Date(fundraiser.startedAt).toLocaleDateString(
    "uk-UA",
  );

  const getStatusLabel = (status: string) => {
    switch (status) {
      case "ACTIVE":
        return "Активний";
      case "FINISHED":
        return "Завершений";
      case "CLOSED":
        return "Зупинений";
    }
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.leftColumn}>
          <span className={styles.label}>Назва збору</span>
          <Link
            to={`/fundraiser/${fundraiser.username}/${fundraiser.slug}`}
            className={styles.titleLink}
          >
            {fundraiser.title}
          </Link>

          <span className={styles.label}>Статус</span>
          <span className={styles.value}>
            {getStatusLabel(fundraiser.status)}
          </span>

          <span className={styles.label}>Всього донатів</span>
          <span className={styles.value}>{fundraiser.totalDonationsCount}</span>

          <span className={styles.label}>Зібрано коштів</span>
          <span className={`${styles.value} ${styles.balance}`}>
            {fundraiser.balance} ₴
          </span>
        </div>

        <div className={styles.rightColumn}>
          <span className={styles.label}>Дата створення</span>
          <span className={styles.value}>{formattedDate}</span>
        </div>
      </div>

      <div className={styles.actions}>
        <button
          className={styles.defaultButton}
          onClick={() => onAddUpdate(fundraiser.id)}
        >
          Додати апдейт
        </button>
        <button
          className={styles.defaultButton}
          onClick={() => onEdit(fundraiser.slug)}
        >
          Редагувати
        </button>
        <button
          className={styles.closeButton}
          onClick={() => onClose(fundraiser.id)}
        >
          Закрити
        </button>
      </div>
    </div>
  );
};

export default FundraiserCard;
