require 'matrix'

module MultisetsHelper
  @@available_metrics = [
    :tanimoto_distance,
    :tanimoto_distance2,
    :normal_tanimoto_distance,
    :normal_tanimoto_distance2
  ]

  def available_metric?(metric = nil)
    @@available_metrics.include?(metric.to_sym)
  end

  def set_metric(metric = :tanimoto_distance)
    @metric = metric.to_sym if @@available_metrics.include?(metric.to_sym)
  end

  def distance(multiset_a = [], multiset_b = [])
    @metric ||= :tanimoto_distance
    (multiset_a.empty? && multiset_b.empty?) ? 0.0 : send(@metric, multiset_a, multiset_b)
  end

  def compact_notation(multiset = [])
    multiset.inject(Hash.new(0)) { |hash, item| hash[item] += 1; hash }
  end

  def multiplicity(multiset = [], element)
    compact_notation(multiset)[element]
  end

  def normalize_multiset(multiset = [])
    __normalize_multiset(compact_notation(multiset))
  end

  private

  def tanimoto_distance(multiset_a = [], multiset_b = [])
    compact_multiset_a = compact_notation(multiset_a)
    compact_multiset_b = compact_notation(multiset_b)

    __tanimoto_distance(compact_multiset_a, compact_multiset_b)
  end

  def tanimoto_distance2(multiset_a = [], multiset_b = [])
    compact_multiset_a = compact_notation(multiset_a)
    compact_multiset_b = compact_notation(multiset_b)

    __tanimoto_distance2(compact_multiset_a, compact_multiset_b)
  end

  def normal_tanimoto_distance(multiset_a = [], multiset_b = [])
    compact_multiset_b = normalize_multiset(multiset_a)
    compact_multiset_a = normalize_multiset(multiset_b)

    __tanimoto_distance(compact_multiset_a, compact_multiset_b)
  end

  def normal_tanimoto_distance2(multiset_a = [], multiset_b = [])
    compact_multiset_b = normalize_multiset(multiset_a)
    compact_multiset_a = normalize_multiset(multiset_b)

    __tanimoto_distance2(compact_multiset_a, compact_multiset_b)
  end

  def derive_canonical_vectors(compact_multiset_a = {}, compact_multiset_b = {})
    base = base_vector(compact_multiset_a, compact_multiset_b)

    base_mapping_a = base_mapping(base, compact_multiset_a)
    base_mapping_b = base_mapping(base, compact_multiset_b)

    vector_a = Vector.elements(base_mapping_a.values)
    vector_b = Vector.elements(base_mapping_b.values)

    [vector_a, vector_b]
  end

  def base_vector(compact_multiset_a = {}, compact_multiset_b = {})
    (compact_multiset_a.keys + compact_multiset_b.keys).uniq.sort
  end

  def base_mapping(base_vector = [], compact_multiset = {})
    base_vector.inject(Hash.new(0)) { |hash, item| hash[item] += compact_multiset[item]; hash }
  end

  def __normalize_multiset(compact_multiset = {})
    outcome = Hash.new(0)

    sum = compact_multiset.values.reduce(:+).to_f
    compact_multiset.each { |k, v| outcome[k] = (v / sum) * 100  }

    outcome
  end

  def __tanimoto_distance(compact_multiset_a = {}, compact_multiset_b = {})
    vector_a, vector_b = derive_canonical_vectors(compact_multiset_a, compact_multiset_b)

    a_dot_b = vector_a.inner_product(vector_b)
    a_dot_b / (vector_a.magnitude + vector_b.magnitude + a_dot_b)
  end

  def __tanimoto_distance2(compact_multiset_a = {}, compact_multiset_b = {})
    vector_a, vector_b = derive_canonical_vectors(compact_multiset_a, compact_multiset_b)

    a_dot_b = vector_a.inner_product(vector_b)
    a_square_norm = vector_a.magnitude * vector_a.magnitude
    b_square_norm = vector_b.magnitude * vector_b.magnitude

    a_dot_b / (a_square_norm + b_square_norm - a_dot_b)
  end
end
