package application.pearson;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.math.BigDecimal;
import java.util.Map;

import application.objects.structure.PearsonComparable;
import org.springframework.stereotype.Service;

@Service
public class PearsonCorrelationService {

	private static final double POWER_OF_TWO = 2.0;

	public double pearson(PearsonComparable comparableA, PearsonComparable comparableB){

		double comparableASum = 0, comparableBSum = 0, comparableAsq = 0, comparableBsq = 0, pSum = 0;

		Map<String, Double> comparableAWords = comparableA.getWordsToAmountUsed();
		Map<String, Double> comparableBWords = comparableB.getWordsToAmountUsed();

		int sharedAmountOfUsedWords = comparableAWords.values().size();

		for(Map.Entry<String, Double> wordUsage : comparableA.getWordsToAmountUsed().entrySet()){
			String word = wordUsage.getKey();
			double amountOfUsesByA = wordUsage.getValue();
			double amountOfUsesByB = comparableBWords.get(word);

			comparableASum += amountOfUsesByA;
			comparableBSum += amountOfUsesByB;

			comparableAsq += Math.pow(amountOfUsesByA, POWER_OF_TWO);
			comparableBsq += Math.pow(amountOfUsesByB, POWER_OF_TWO);

			pSum += amountOfUsesByA * amountOfUsesByB;

		}

		double numerator = pSum - ((comparableASum * comparableBSum) / sharedAmountOfUsedWords);

		double userAResult = comparableAsq - (Math.pow(comparableASum, POWER_OF_TWO) / sharedAmountOfUsedWords);
		double userBResult = comparableBsq - (Math.pow(comparableBSum, POWER_OF_TWO) / sharedAmountOfUsedWords);
		double denominator = Math.sqrt(userAResult * userBResult);

		return new BigDecimal(numerator / denominator).setScale(3, ROUND_HALF_UP).doubleValue();
	}

}
