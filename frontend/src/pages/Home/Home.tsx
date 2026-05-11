import styles from "./Home.module.css";
import useNewest from "./useNewest.ts";
import { Card } from "@/pages/Home/Card/Card.tsx";

export const Home = () => {
  const { data: newest = [] } = useNewest();

  return (
    <div className={styles.wrapper}>
      <p className={styles.text}>Нові збори:</p>

      <div className={styles.cards}>
        {newest.map((fundraiser) => (
          <Card key={fundraiser.id} {...fundraiser} />
        ))}
      </div>
    </div>
  );
};
