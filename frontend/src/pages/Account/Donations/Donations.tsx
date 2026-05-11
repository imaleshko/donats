import styles from "./Donations.module.css";
import { Link } from "react-router";
import { useDonations } from "@/pages/Account/Donations/useDonations.ts";

const Donations = () => {
  const { data: donations, isLoading } = useDonations();

  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Мої донати</h2>

      <div className={styles.divider} />

      {isLoading ? (
        <p className={styles.empty}>Завантаження...</p>
      ) : !donations || donations.length === 0 ? (
        <p className={styles.empty}>Ви ще не зробили жодного донату.</p>
      ) : (
        <div className={styles.list}>
          {donations.map((donation) => {
            const date = new Date(donation.createdAt).toLocaleDateString(
              "uk-UA",
            );

            return (
              <div key={donation.id} className={styles.item}>
                <div className={styles.linkText}>
                  <span>Донат на збір: </span>
                  <Link
                    to={`/fundraiser/${donation.authorUsername}/${donation.fundraiserSlug}`}
                    className={styles.link}
                  >
                    {donation.fundraiserTitle}
                  </Link>
                </div>

                <div className={styles.header}>
                  <span className={styles.name}>{donation.name}</span>
                  <span className={styles.amount}>{donation.amount} ₴</span>
                  <span className={styles.date}>{date}</span>
                </div>

                {donation.message && (
                  <p className={styles.message}>{donation.message}</p>
                )}
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Donations;
