package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int INIT_CAPACITY = 100;
    private final int GROWTH_CAPACITY = 50;
    private Resume[] storage = new Resume[INIT_CAPACITY];
    private int size = 0;

    /**
     * clear storage
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * ensure capacity of storage, if limit reached
     */
    private void ensureCapacity() {
        int length = storage.length;
        if (length == size) {
            storage = Arrays.copyOf(storage, length + GROWTH_CAPACITY);
        }
    }

    /**
     * find webapp.model.Resume by uuid, if storage does not contain it
     * @return -1, if was not found
     */
    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * save webapp.model.Resume, if storage does not contain it
     */
    public void save(Resume resume) {
        ensureCapacity();
        String uuid = resume.getUuid();
        int index = find(uuid);
        if (index != -1) {
            outAlreadyInStorage(uuid);
            return;
        }
        storage[size++] = resume;
    }

    /**
     * update webapp.model.Resume, if storage contains it
     */
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = find(uuid);
        if (index == -1) {
            outNotFound(uuid);
            return;
        }
        storage[index] = resume;
    }

    /**
     * get webapp.model.Resume by uuid, if storage contains it
     */
    public Resume get(String uuid) {
        int index = find(uuid);
        if (index == -1) {
            outNotFound(uuid);
            return null;
        }
        return storage[index];
    }

    /**
     * delete webapp.model.Resume by uuid, if storage contains it
     */
    public void delete(String uuid) {
        int index = find(uuid);
        if (index == -1) {
            outNotFound(uuid);
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return amount of Resumes in storage (without null)
     */
    public int size() {
        return size;
    }

    /**
     * out error "was not found"
     */
    private void outNotFound(String uuid) {
        outErrorMessage(String.format("webapp.model.Resume with uuid={%s} was not found!", uuid));
    }

    /**
     * out error "already in storage"
     */
    private void outAlreadyInStorage(String uuid) {
        outErrorMessage(String.format("webapp.model.Resume with uuid={%s} is already in storage!", uuid));
    }

    /**
     * out error
     */
    private void outErrorMessage(String message) {
        System.out.println(message);
    }
}
