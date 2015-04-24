package vs.db.util.sql;

public class SelectQueryBuilder extends SqlQueryBuilder {

    public static enum SelectionType {
        EVERYTHING("*"),
        COUNT("COUNT(*)");

        private String str;

        private SelectionType(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public SelectQueryBuilder(SelectionType selectionType, String table) {
        stringBuilder.append("SELECT ").append(selectionType.toString());
        stringBuilder.append(" FROM ").append(table);
    }

    public SelectQueryBuilder limit(long limit) {
        stringBuilder.append(" LIMIT ").append(limit);
        return this;
    }

    public SelectQueryBuilder offset(long offset) {
        stringBuilder.append(" OFFSET ").append(offset);
        return this;
    }

}
