import styles from "./Tag.module.css";

interface TagProps {
  name: string;
  isActive: boolean;
  onClick: (tag: string | null) => void;
}

export const Tag = ({ name, isActive, onClick }: TagProps) => {
  return (
    <button
      key={name}
      className={`${styles.tag} ${isActive ? styles.active : ""}`}
      onClick={() => onClick(isActive ? null : name)}
    >
      {name}
    </button>
  );
};
