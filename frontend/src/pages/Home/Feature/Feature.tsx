import styles from "./Feature.module.css";
import { Divider } from "@/components/Divider/Divider.tsx";

interface FeatureProps {
  title: string;
  description: string;
  index: number;
}

export const Feature = ({ title, description, index }: FeatureProps) => {
  return (
    <div key={index} className={styles.wrapper}>
      <h3 className={styles.featureTitle}>{title}</h3>
      <Divider />
      <p className={styles.featureDescription}>{description}</p>
    </div>
  );
};
