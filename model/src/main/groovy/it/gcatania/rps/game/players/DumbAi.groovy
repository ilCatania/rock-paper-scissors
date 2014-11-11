package it.gcatania.rps.game.players

/**
 * @author ilCatania
 */
class DumbAi extends BasePlayer{

    int signId = -1

    @Override
    protected int chooseSignId(int maxSigns) {
        if(signId < 0) {
            signId = new Random().nextInt(maxSigns)
        }
        return Math.min(signId, maxSigns-1)
    }
}
