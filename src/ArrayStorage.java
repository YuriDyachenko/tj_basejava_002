import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_INIT_SIZE = 100;
    private final int STORAGE_GROW_SIZE = 50;
    private Resume[] storage = new Resume[STORAGE_INIT_SIZE];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    private void checkAndGrowIfNeed() {
        int length = storage.length;
        if (length == size) {
            storage = Arrays.copyOf(storage, length + STORAGE_GROW_SIZE);
        }
    }

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume r) {
        checkAndGrowIfNeed();
        int found = find(r.getUuid());
        if (found != -1) {
            storage[found] = r;
        } else {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int found = find(uuid);
        if (found == -1) {
            return null;
        }
        return storage[found];
    }

    public void delete(String uuid) {
        int found = find(uuid);
        if (found == -1) {
            return;
        }
        if ((size - 1 - found) >= 0) {
            System.arraycopy(storage, found + 1, storage, found, size - 1 - found);
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
