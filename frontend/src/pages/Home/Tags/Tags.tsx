import styles from "./Tags.module.css";
import { useTags } from "@/pages/Home/useTags.ts";
import { Tag } from "@/components/Tag/Tag.tsx";

interface TagsProps {
  activeTag: string | null;
  onChange: (tag: string | null) => void;
}

export const Tags = ({ activeTag, onChange }: TagsProps) => {
  const { data: tags = [] } = useTags();

  if (tags.length === 0) return null;

  return (
    <div className={styles.tags}>
      {tags.map((tag) => {
        const isActive = tag.name === activeTag;
        return (
          <Tag
            key={tag.name}
            name={tag.name}
            isActive={isActive}
            onClick={onChange}
          />
        );
      })}
    </div>
  );
};
