package com.segeval.safedrive.commands.protocol;


import com.segeval.safedrive.commands.ObdCommand;

/**
 * Reset trouble codes.
 *
 * @author pires
 * @version $Id: $Id
 */
public class ResetTroubleCodesCommand extends ObdCommand {

    /**
     * <p>Constructor for ResetTroubleCodesCommand.</p>
     */
    public ResetTroubleCodesCommand() {
        super("04");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performCalculations() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFormattedResult() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCalculatedResult() {
        return getResult();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getResult();
    }

}
