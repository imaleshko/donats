import styles from "./DonationsHistory.module.css";
import type { Donation } from "../../fundraiserPageApi.ts";

interface DonationsHistoryProps {
  donations: Donation[];
}

const DonationsHistory = ({ donations }: DonationsHistoryProps) => {
  if (!donations || donations.length === 0) return null;

  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Історія донатів</h2>

      <div className={styles.list}>
        {donations.map((donation) => {
          const date = new Date(donation.createdAt).toLocaleDateString("uk-UA");

          return (
            <div key={donation.id} className={styles.item}>
              <div className={styles.header}>
                <span className={styles.name}>{donation.name}</span>
                <span className={styles.amount}>{donation.amount} ₴</span>
                <span>{date}</span>
              </div>

              {donation.message && (
                <p className={styles.message}>{donation.message}</p>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default DonationsHistory;
