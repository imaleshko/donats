import styles from "./Info.module.css";
import type { Fundraiser } from "../../../Home/homeApi.ts";

interface InfoProps {
  fundraiser: Fundraiser;
}

const Info = ({ fundraiser }: InfoProps) => {
  const progressPercentage = fundraiser.goal
    ? Math.min(Math.round((fundraiser.balance / fundraiser.goal) * 100), 100)
    : 0;

  const startDate = new Date(fundraiser.startedAt).toLocaleDateString("uk-UA");
  const endedAt = fundraiser.endedAt
    ? new Date(fundraiser.endedAt).toLocaleDateString("uk-UA")
    : null;

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

      <div className={styles.dates}>
        <p>Збір розпочато: {startDate}</p>
        {endedAt && <p>Збір завершено: {endedAt}</p>}
      </div>

      <div className={styles.progress}>
        <div
          className={styles.progressOuter}
          style={{
            background: `conic-gradient(var(--yellow-color) ${progressPercentage}%, var(--grey-color) 0deg)`,
          }}
        >
          <div className={styles.progressInner}>
            <span className={styles.percentageText}>{progressPercentage}%</span>
            <span className={styles.balanceText}>
              {fundraiser.balance} / {fundraiser.goal ?? "∞"}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Info;
