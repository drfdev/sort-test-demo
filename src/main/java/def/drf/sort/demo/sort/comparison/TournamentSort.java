package def.drf.sort.demo.sort.comparison;

import def.drf.sort.demo.AbstractSorter;
import def.drf.sort.demo.metric.MetricBucket;
import def.drf.sort.demo.snap.Snapshoter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public final class TournamentSort<T> extends AbstractSorter<T> {
    private static class Match<P> {
        Match<P> losers;
        Match<P> winners;
        P winner;

        public Match(P player) {
            this.winner = player;
        }

        public Match(Comparator<P> comparator, Match<P> a, Match<P> b) {
            boolean result = comparator.compare(a.winner, b.winner) < 0;
            this.losers = result ? b : a;
            this.winners = result ? a : b;
            this.winner = result ? a.winner : b.winner;
        }

        public boolean isPlayer() {
            return losers == null || winners == null;
        }

        /*
        public Match<P> rebuildFunctional(Comparator<P> comparator) {
            return winners.isPlayer()
                    ? losers
                    : new Match<P>(comparator, losers, winners.rebuildFunctional(comparator));
        }
         */

        public Match<P> rebuildImperative(Comparator<P> comparator) {
            if (winners.isPlayer()) {
                return losers;
            } else {
                winners = winners.rebuildImperative(comparator);
                if (comparator.compare(losers.winner, winners.winner) < 0) {
                    winner = losers.winner;
                    Match<P> temp = losers;
                    losers = winners;
                    winners = temp;
                } else {
                    winner = winners.winner;
                }
                return this;
            }
        }

        /*
        public Match<P> rebuildIterative(Comparator<P> comparator, Deque<Match<P>> stack) {
            if (winners.isPlayer()) {
                return losers;
            }
            final Match<P> root = this;
            Match<P> tourney = this;
            while (!tourney.winners.isPlayer()) {
                stack.push(tourney);
                tourney = tourney.winners;
            }
            stack.peek().winners = tourney.losers;
            while (!stack.isEmpty()) {
                Match<P> t = stack.pop();
                if (comparator.compare(t.losers.winner, t.winners.winner) < 0) {
                    t.winner = t.losers.winner;
                    Match<P> temp = t.losers;
                    t.losers = t.winners;
                    t.winners = temp;
                } else {
                    t.winner = t.winners.winner;
                }
            }
            return root;
        }
         */
    }

    private static final class Popper<P> {
        Match<P> tourney;
        Comparator<P> comparator;

        public Popper(Match<P> tourney, Comparator<P> comparator) {
            this.tourney = tourney;
            this.comparator = comparator;
        }

        private P pop() {
            P result = tourney.winner;
            tourney = tourney.isPlayer() ? null : tourney.rebuildImperative(comparator);
            return result;
        }
    }

    public TournamentSort(@NotNull Comparator<T> comparator) {
        super(comparator);
    }

    public TournamentSort(@NotNull Comparator<T> comparator,
                          @Nullable MetricBucket metrics) {
        super(comparator, metrics);
    }

    public TournamentSort(@NotNull Comparator<T> comparator,
                          @Nullable Snapshoter<T> snapshoter,
                          @Nullable MetricBucket metrics) {
        super(comparator, snapshoter, metrics);
    }

    @Override
    public void sort(@NotNull List<T> values) {
        /* https://en.wikipedia.org/wiki/Tournament_sort */
        Match<T> tourney = knockout(values, 0, values.size() - 1);
        Popper<T> popper = new Popper<>(tourney, comparator);

        for (int i = 0; i < values.size(); i++) {
            values.set(i, popper.pop());
        }
    }

    private Match<T> knockout(List<T> values, int i, int k) {
        if (i == k) {
            return new Match<>(values.get(i));
        }
        int j = (i + k) / 2;
        return new Match<T>(comparator, knockout(values, i, j), knockout(values, j + 1, k));
    }
}
