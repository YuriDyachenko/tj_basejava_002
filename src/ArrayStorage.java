import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int INIT_CAPACITY = 100;
    private final int GROWTH_CAPACITY = 50;
    private Resume[] storage = new Resume[INIT_CAPACITY];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    private void ensureCAPACITY() {
        int length = storage.length;
        if (length == size) {
            storage = Arrays.copyOf(storage, length + GROWTH_CAPACITY);
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

    public void save(Resume resume) {
        ensureCAPACITY();
        int index = find(resume.getUuid());
        if (index == -1) {
            storage[size++] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = find(uuid);
        if (index == -1) {
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = find(uuid);
        if (index == -1) {
            return;
        }
        int shift = size - 1 - index;
        if (shift > 0) {
            System.arraycopy(storage, index + 1, storage, index, shift);
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
