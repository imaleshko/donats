import styles from "./TopDonations.module.css";
import type { Donation } from "../../fundraiserPageApi.ts";

interface TopDonationsProps {
  donations: Donation[];
}

const TopDonations = ({ donations }: TopDonationsProps) => {
  if (!donations || donations.length === 0) return null;

  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Топ донатів</h2>

      <div className={styles.list}>
        {donations.map((donation) => (
          <div key={donation.id} className={styles.item}>
            <span className={styles.name}>{donation.name}</span>
            <span className={styles.amount}>{donation.amount} ₴</span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TopDonations;
