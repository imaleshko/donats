import styles from "./Info.module.css";
import type { Fundraiser } from "@/pages/Home/homeApi.ts";
import { Divider } from "@/components/Divider/Divider.tsx";

interface InfoProps {
  fundraiser: Fundraiser;
}

const Info = ({ fundraiser }: InfoProps) => {
  const progressPercentage = fundraiser.goal
    ? Math.min(Math.round((fundraiser.balance / fundraiser.goal) * 100), 100)
    : 0;

  const formatDate = (date: string) => {
    return new Date(date).toLocaleDateString("uk-UA");
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.user}>
        {fundraiser.authorAvatarUrl && (
          <div className={styles.avatarWrapper}>
            <img
              className={styles.avatar}
              src={fundraiser.authorAvatarUrl}
              alt=""
            />
          </div>
        )}
        <span className={styles.username}>{fundraiser.authorUsername}</span>
      </div>

      <Divider />

      <div className={styles.dates}>
        <p>Збір розпочато: {formatDate(fundraiser.startedAt)}</p>
        {fundraiser.closedAt && (
          <p>Збір завершено: {formatDate(fundraiser.closedAt)}</p>
        )}
      </div>

      <div className={styles.progress}>
        <div className={styles.circle}>
          <span className={styles.percentageText}>{progressPercentage}%</span>
          <span>
            {fundraiser.balance} / {fundraiser.goal ?? "∞"}
          </span>
        </div>
      </div>
    </div>
  );
};

export default Info;
