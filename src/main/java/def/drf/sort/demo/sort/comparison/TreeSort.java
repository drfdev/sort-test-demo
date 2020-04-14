package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class TreeSort<T> extends AbstractSorter<T> {
    public static class Node<T> {
        T key;
        Node<T> left;
        Node<T> right;

        public Node(T key) {
            this.key = key;
        }
    }

    private Node<T> root;
    private AtomicInteger index;

    public TreeSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public TreeSort(@NotNull Comparator<T> comparator,
                    @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public TreeSort(@NotNull Comparator<T> comparator,
                    @Nullable Snapshoter<T> snapshoter,
                    @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Tree_sort */
        root = null;
        index = new AtomicInteger(0);

        treeins(values);
        inorderRec(values, root);

        // How to calculate metric ??
    }

    private void treeins(List<T> values) {
        for(T val : values) {
            insert(val);
        }
    }

    private void insert(T key) {
        root = insertRec(root, key);
    }

    private void inorderRec(List<T> values, Node<T> root) {
        if (root != null) {
            inorderRec(values, root.left);
            values.set(index.getAndIncrement(), root.key);
            inorderRec(values, root.right);
        }
    }

    private Node<T> insertRec(Node<T> root, T key) {
        if (root == null) {
            root = new Node<T>(key);
            return root;
        }

        if (comparator.compare(key, root.key) < 0) {
            root.left = insertRec(root.left, key);
        } else if (comparator.compare(key, root.key) > 0) {
            root.right = insertRec(root.right, key);
        }

        return root;
    }
}
