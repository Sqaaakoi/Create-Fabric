package com.simibubi.create.content.logistics.block.display.source;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.components.clock.CuckooClockTileEntity;
import com.simibubi.create.content.logistics.block.display.DisplayLinkContext;
import com.simibubi.create.content.logistics.block.display.target.DisplayTargetStats;
import com.simibubi.create.content.logistics.trains.management.display.FlapDisplaySection;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TimeOfDayDisplaySource extends SingleLineDisplaySource {

	public static final MutableComponent EMPTY_TIME = Components.literal("--:--");
	public static final MutableComponent EMPTY_TIME_COMPACT = Components.literal("----");
	@Override
	protected MutableComponent provideLine(DisplayLinkContext context, DisplayTargetStats stats) {
		boolean compact = stats.maxColumns() <= 4;
		MutableComponent emptyTime = compact ? EMPTY_TIME_COMPACT : EMPTY_TIME;
		if (!(context.level()instanceof ServerLevel sLevel))
			return emptyTime;
		if (!(context.getSourceTE() instanceof CuckooClockTileEntity ccte))
			return emptyTime;
		if (ccte.getSpeed() == 0)
			return emptyTime;

		boolean c12 = context.sourceConfig()
			.getInt("Cycle") < 2;
		boolean isNatural = sLevel.dimensionType()
			.natural();

		int dayTime = (int) (sLevel.getDayTime() % 24000);
		int hours = (dayTime / 1000 + 6) % 24;
		int minutes = (dayTime % 1000) * 60 / 1000;
		MutableComponent suffix = Lang.translateDirect("generic.daytime." + (hours > 11 ? "pm" : "am"));

		if (context.sourceConfig().getInt("Cycle") % 2 == 0)
			minutes = minutes / 5 * 5;
		if (c12) {
			hours %= 12;
			if (hours == 0)
				hours = 12;
		}

		if (!isNatural) {
			hours = Create.RANDOM.nextInt(70) + 24;
			minutes = Create.RANDOM.nextInt(40) + 60;
		}

		MutableComponent component = Components.literal(
			(hours < 10 ? (!c12 || compact ? "0" : " ") : "") + hours + (compact ? "" : ":") + (minutes < 10 ? "0" : "") + minutes + (c12 ? " " : ""));

		return c12 ? component.append(suffix) : component;
	}

	@Override
	protected String getFlapDisplayLayoutName(DisplayLinkContext context) {
		return "Instant";
	}

	@Override
	protected FlapDisplaySection createSectionForValue(DisplayLinkContext context, int size) {
		return new FlapDisplaySection(size * FlapDisplaySection.MONOSPACE, "instant", false, false);
	}

	@Override
	protected String getTranslationKey() {
		return "time_of_day";
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void initConfigurationWidgets(DisplayLinkContext context, ModularGuiLineBuilder builder, boolean isFirstLine) {
		super.initConfigurationWidgets(context, builder, isFirstLine);
		if (isFirstLine)
			return;

		builder.addSelectionScrollInput(0, 130, (si, l) -> {
			si.forOptions(Lang.translatedOptions("display_source.time", "12_hour", "12_hour_accurate",
				"24_hour", "24_hour_accurate")).titled(Lang.translateDirect("display_source.time.format"));
		}, "Cycle");
	}

	@Override
	protected boolean allowsLabeling(DisplayLinkContext context) {
		return true;
	}

	@Override
	public int getPassiveRefreshTicks() {
		return 10;
	};

}
